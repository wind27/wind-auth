FROM centos-jdk-maven:7.8.3

MAINTAINER qianchun, qianchun27@hotmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV BASE_INSTALL_DIR /opt/install

RUN mkdir -p ${BASE_INSTALL_DIR}

COPY authorized_keys ${BASE_INSTALL_DIR}
COPY user.sh ${BASE_INSTALL_DIR}
COPY install.sh ${BASE_INSTALL_DIR}
COPY restart.sh ${BASE_INSTALL_DIR}
COPY filebeat_restart.sh ${BASE_INSTALL_DIR}
COPY filebeat.sh ${BASE_INSTALL_DIR}
COPY filebeat.yml ${BASE_INSTALL_DIR}

RUN sh /opt/install/install.sh