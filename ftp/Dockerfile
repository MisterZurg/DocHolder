FROM alpine:3.13
RUN apk --no-cache add vsftpd

COPY start_vsftpd.sh /bin/start_vsftpd.sh
COPY vsftpd.conf /etc/vsftpd/vsftpd.conf

EXPOSE 21 21000-21010
VOLUME /ftp/ftp

STOPSIGNAL SIGKILL

RUN chmod +x /bin/start_vsftpd.sh

RUN cat /bin/start_vsftpd.sh | tr -d '\r' > /bin/start_vsftpd.sh
ENTRYPOINT ["/bin/start_vsftpd.sh"]

