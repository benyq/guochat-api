# Docker image for springboot file run
# VERSION 0.0.1
# Author: benyq
# 基础镜像使用java
FROM java:8
# 作者
MAINTAINER benyq <yzjbenyq@gmail.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
ENV LANG C.UTF-8
ENV LANGUAGE zh_CN.UTF-8
ENV LC_ALL C.UTF-8
ENV TZ Asia/Shanghai
# 将jar包添加到容器中并更名为app.jar
ADD /build/libs/guochat-api.jar app.jar
# 运行jar包
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8","-Dsun.jnu.encoding=UTF-8","-jar","/app.jar"]