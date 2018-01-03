(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FakerTopoController', FakerTopoController);

    FakerTopoController.$inject = ['$state', 'Location', '$q', '$timeout', '$rootScope', 'CountPath', 'toaster'];

    function FakerTopoController($state, Location, $q, $timeout, $rootScope, CountPath, toaster) {

        var vm = this;
        vm.result = {};
        vm.dstFilter = dstFilter;
        vm.src = vm.data.locationElectricBikes[0].location.positionX;
        vm.dst = null;
        vm.action= action;
        vm.primary = null;
        vm.second = [];
        //console.log(vm.primary);

        function dstFilter(e) {
            return e.positionX !== vm.src;
        }

        function action(data) {
            switch (data) {
                case 'recommend':
                    CountPath.save({src: vm.src, dst:vm.dst.positionX}, {}, function success(result) {
                        vm.primary = {};
                        vm.primary.ids = result.primary;
                        vm.primary.links = result.primaryLinks;
                        vm.primary.location = result.primaryLocations;
                        console.log(vm.primary.links);
                        //toaster.pop('success', ' ', '已生成');
                    }, function error(result) {
                        toaster.pop('error', ' ', result.data.message);
                    });
                    break;
                case 'drive':
                    $state.go('location');
                    break;
                case 'bike':
                    $state.go('location-electric-bike');
                    break;
            }

        }





        // d3
        var deferA = $q.defer();
        setTimeout(function () {
            Location.query(function (result) {
                vm.result = result;
                deferA.resolve()
            });
        }, 0);

        var p = $q.all({
            dataA: deferA.promise,
        })
        p.then(function () {

            var width = 800,
                height = 500,
                colors = d3.scale.category10();

            var svg = d3.select('svg')
                .attr('oncontextmenu', 'return false;');

            var nodes = [];
            var links = [];
            for (var i = 0; i <= vm.result.locationList.length - 1; i++) {
                var node = {};
                node.id = vm.result.locationList[i].positionX;
                nodes.push(node);
            }
            for (var i = 0; i <= vm.result.paths.length - 1; i++) {
                var link = {};
                link.source = nodes[vm.result.paths[i].fromWhere.positionX];
                link.target = nodes[vm.result.paths[i].toWhere.positionX];
                link.length = vm.result.paths[i].length;
                links.push(link);
            }

            var lastNodeId = vm.result.locationList.length - 2;
// init D3 force layout
            var force = d3.layout.force()
                .nodes(nodes)
                .links(links)
                .size([width, height])
                .linkDistance(60)
                .charge(-50 * (lastNodeId + 2))
                //加一个配置表
                .on('tick', tick);
                //console.log(-166 * (lastNodeId + 2));


// line displayed when dragging new nodes
            var drag_line = svg.append('svg:path')
                .attr('class', 'link dragline hidden')
                .attr('d', 'M0,0L0,0');

// handles to link and node element groups
            var length = svg.append('svg:g').selectAll('text');
            var path = svg.append('svg:g').selectAll('path'),
                circle = svg.append('svg:g').selectAll('g');

// mouse event vars
            var selected_node = null,
                selected_link = null,
                mousedown_link = null,
                mousedown_node = null,
                mouseup_node = null;

            function resetMouseVars() {
                mousedown_node = null;
                mouseup_node = null;
                mousedown_link = null;
            }

// update force layout (called automatically each iteration)
            function tick() {
                // draw directed edges with proper padding from node centers
                path.attr('d', function (d) {
                    var deltaX = d.target.x - d.source.x,
                        deltaY = d.target.y - d.source.y,
                        dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY),
                        normX = deltaX / dist,
                        normY = deltaY / dist,
                        sourcePadding = d.left ? 12 : 12,
                        targetPadding = d.right ? 12 : 12,
                        sourceX = d.source.x + (sourcePadding * normX),
                        sourceY = d.source.y + (sourcePadding * normY),
                        targetX = d.target.x - (targetPadding * normX),
                        targetY = d.target.y - (targetPadding * normY);
                    return 'M' + sourceX + ',' + sourceY + 'L' + targetX + ',' + targetY;
                });

                circle.attr('transform', function (d) {
                    return 'translate(' + d.x + ',' + d.y + ')';
                });
            }

// update graph (called when needed)
            function restart() {
                $rootScope.nodes = nodes;
                $rootScope.links = links;
                // path (link) group
                path = path.data(links);
                length = length.data(links);
                // update existing links
                path.classed('selected', function (d) {
                    return d === selected_link;
                });


                // add new links
                path.enter().append('svg:path')
                    .attr('class', 'link')
                    .attr('id', function (d, i) {
                        return 'path' + i
                    })
                    .classed('selected', function (d) {
                        return d === selected_link;
                    })
                    .on('mousedown', function (d) {
                        if (d3.event.ctrlKey) return;

                        // select link
                        mousedown_link = d;
                        if (mousedown_link === selected_link) selected_link = null;
                        else selected_link = mousedown_link;
                        selected_node = null;
                        restart();
                    });
                length.enter().append('svg:text')
                    .attr('x', 30)
                    .attr('y', 0)
                    .attr('class', 'id')
                    .style('font-size', '15px')
                    .append('textPath').attr('xlink:href', function (d, i) {
                    return '#path' + i;
                })
                    .text(function (d) {
                        //console.log(d);
                        return d.length;
                    });
                // remove old links
                path.exit().remove();


                // circle (node) group
                // NB: the function arg is crucial here! nodes are known by id, not by index!
                circle = circle.data(nodes, function (d) {
                    return d.id;
                });

                // update existing nodes (reflexive & selected visual states)
                circle.selectAll('circle')
                    .style('fill', function (d) {
                        return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id);
                    })
                    .classed('reflexive', function (d) {
                        return d.reflexive;
                    });

                // add new nodes
                var g = circle.enter().append('svg:g');

                g.append('svg:circle')
                    .attr('class', 'node')
                    .attr('r',  function (d) {
                        return (d.id === vm.src) ? 20 : 12;
                    })
                    .style('fill', function (d) {
                        return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id);
                    })
                    .style('stroke', function (d) {
                        return d3.rgb(colors(d.id)).darker().toString();
                    })
                    .classed('reflexive', function (d) {
                        return d.reflexive;
                    })
                    .on('mouseover', function (d) {
                        if (!mousedown_node || d === mousedown_node) return;
                        // enlarge target node
                        d3.select(this).attr('transform', 'scale(1.1)');
                    })
                    .on('mouseout', function (d) {
                        if (!mousedown_node || d === mousedown_node) return;
                        // unenlarge target node
                        d3.select(this).attr('transform', '');
                    })
                    .on('mousedown', function (d) {
                        if (d3.event.ctrlKey) return;

                        // select node
                        mousedown_node = d;
                        if (mousedown_node === selected_node) selected_node = null;
                        else selected_node = mousedown_node;
                        selected_link = null;

                        // reposition drag line
                        drag_line
                            .style('marker-end', 'url(#end-arrow)')
                            .classed('hidden', false)
                            .attr('d', 'M' + mousedown_node.x + ',' + mousedown_node.y + 'L' + mousedown_node.x + ',' + mousedown_node.y);

                        restart();
                    })
                    .on('mouseup', function (d) {
                        if (!mousedown_node) return;

                        // needed by FF
                        drag_line
                            .classed('hidden', true)
                            .style('marker-end', '');

                        // check for drag-to-self
                        mouseup_node = d;
                        if (mouseup_node === mousedown_node) {
                            resetMouseVars();
                            return;
                        }

                        // unenlarge target node
                        d3.select(this).attr('transform', '');

                        // add link to graph (update if exists)
                        // NB: links are strictly source < target; arrows separately specified by booleans
                        var source, target, direction;
                        if (mousedown_node.id < mouseup_node.id) {
                            source = mousedown_node;
                            target = mouseup_node;
                            direction = 'right';
                        } else {
                            source = mouseup_node;
                            target = mousedown_node;
                            direction = 'left';
                        }

                        var link;
                        link = links.filter(function (l) {
                            return (l.source === source && l.target === target);
                        })[0];

                        if (link) {
                            link[direction] = true;
                        } else {
                            link = {source: source, target: target, left: false, right: false};
                            link[direction] = true;
                            links.push(link);
                        }

                        // select new link
                        selected_link = link;
                        selected_node = null;
                        restart();
                    });

                // show node IDs
                g.append('svg:text')
                    .attr('x', 0)
                    .attr('y', 4)
                    .attr('class', 'id')
                    .attr('fill', function (d) {
                        return (d.id === vm.src) ? 'white': 'black'
                    })
                    .text(function (d) {
                        return (d.id === vm.src) ? 'Src' : d.id;
                    });

                // remove old nodes
                circle.exit().remove();

                // set the graph in motion
                force.start();
            }

            function mousedown() {
                // prevent I-bar on drag
                //d3.event.preventDefault();

                // because :active only works in WebKit?
                svg.classed('active', true);

                if (d3.event.ctrlKey || mousedown_node || mousedown_link) return;

                // insert new node at point
                var point = d3.mouse(this),
                    node = {id: ++lastNodeId, reflexive: false};
                node.x = point[0];
                node.y = point[1];
                nodes.push(node);

                restart();
            }

            function mousemove() {
                if (!mousedown_node) return;

                // update drag line
                drag_line.attr('d', 'M' + mousedown_node.x + ',' + mousedown_node.y + 'L' + d3.mouse(this)[0] + ',' + d3.mouse(this)[1]);

                restart();
            }

            function mouseup() {
                if (mousedown_node) {
                    // hide drag line
                    drag_line
                        .classed('hidden', true)
                        .style('marker-end', '');
                }

                // because :active only works in WebKit?
                svg.classed('active', false);

                // clear mouse event vars
                resetMouseVars();
            }

            function spliceLinksForNode(node) {
                var toSplice = links.filter(function (l) {
                    return (l.source === node || l.target === node);
                });
                toSplice.map(function (l) {
                    links.splice(links.indexOf(l), 1);
                });
            }

// only respond once per keydown
            var lastKeyDown = -1;

            function keydown() {
                d3.event.preventDefault();

                if (lastKeyDown !== -1) return;
                lastKeyDown = d3.event.keyCode;

                // ctrl
                if (d3.event.keyCode === 17) {
                    circle.call(force.drag);
                    svg.classed('ctrl', true);
                }

                if (!selected_node && !selected_link) return;
                switch (d3.event.keyCode) {
                    case 8: // backspace
                    case 46: // delete
                        if (selected_node) {
                            nodes.splice(nodes.indexOf(selected_node), 1);
                            spliceLinksForNode(selected_node);
                        } else if (selected_link) {
                            links.splice(links.indexOf(selected_link), 1);
                        }
                        selected_link = null;
                        selected_node = null;
                        restart();
                        break;
                    case 66: // B
                        if (selected_link) {
                            // set link direction to both left and right
                            selected_link.left = true;
                            selected_link.right = true;
                        }
                        restart();
                        break;
                    case 76: // L
                        if (selected_link) {
                            // set link direction to left only
                            selected_link.left = true;
                            selected_link.right = false;
                        }
                        restart();
                        break;
                    case 82: // R
                        if (selected_node) {
                            // toggle node reflexivity
                            selected_node.reflexive = !selected_node.reflexive;
                        } else if (selected_link) {
                            // set link direction to right only
                            selected_link.left = false;
                            selected_link.right = true;
                        }
                        restart();
                        break;
                }
            }

            function keyup() {
                lastKeyDown = -1;

                // ctrl
                if (d3.event.keyCode === 17) {
                    circle
                        .on('mousedown.drag', null)
                        .on('touchstart.drag', null);
                    svg.classed('ctrl', false);
                }
            }

// app starts here
            //  function dataIsReady() {
            // 暂时关闭鼠标事件
//            svg.on('mousedown', mousedown)
//                .on('mousemove', mousemove)
//                .on('mouseup', mouseup);
            restart();
            //  }
        });
    }
})();
