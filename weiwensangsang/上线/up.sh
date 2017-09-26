#!/usr/bin/env bash
#/bin/bash

echo "上线开始:"
./mvnw clean
echo "开始打包:"
./mvnw -Pprod package
echo "上传war包:"
expect 上线/trans.sh
echo "开始编译:"
expect 上线/runRemote.sh
