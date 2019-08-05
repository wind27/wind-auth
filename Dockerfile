FROM centos-jdk-maven:7.8.3

MAINTAINER qianchun, qianchun27@hotmail.com

LABEL version="1.0"

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV BASE_INSTALL_DIR /opt/install

RUN mkdir -p ${BASE_INSTALL_DIR}

RUN sh /opt/install/install.sh