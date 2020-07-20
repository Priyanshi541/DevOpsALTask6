
FROM centos

RUN yum install httpd -y 

COPY /task6_dev/index.php  /var/www/html/

CMD /usr/sbin/httpd  -DFOREGROUND 