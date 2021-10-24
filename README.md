###centos安装docker
https://www.cnblogs.com/qgc1995/archive/2018/08/29/9553572.html
### 修改docker 源镜像
https://www.cnblogs.com/reasonzzy/p/11127359.html

###启动docker  
sudo service docker start  
###停止docker   
sudo service docker stop    
###重启docker  
sudo systemctl restart docker
###重启docker守护进程，开放端口的时候用到  
sudo systemctl daemon-reload  


###docker mysql 启动
docker run -itd -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --name mysql-latest mysql  
docker run -itd -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7
###docker mysql shell
sudo docker exec -it mysql-latest bash
###docker springboot 启动
docker run -d -p 8080:8080 guochatdocker
-d参数是让容器后台运行  
-p 是做端口映射，此时将服务器中的8080端口映射到容器中的8080(项目中端口配置的是8080)端口


### nginx  
https://blog.csdn.net/baidu_21349635/article/details/102738972  
