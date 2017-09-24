#!/usr/bin/env bash
#/bin/bash

echo "上线开始:"
#./mvnw clean
echo "开始打包:"
#./mvnw -Pprod package
echo "打包结束，开始上传数据包:"
expect trans.sh
echo "成功"