import smtplib
from logger import Logger


class EmailSender:
    logger = ""

    def __init__(self, logger):
        self.logger = logger

    def send(self, subject, message):
        gmail_user = "my.app.raspberry@gmail.com"
        gmail_password = "09Thoine"

        server = smtplib.SMTP('smtp.gmail.com', 587)
        server.ehlo()
        server.starttls()
        server.login(gmail_user, gmail_password)

        sent_from = 'myAppRaspberry <my.app.raspberry@gmail.com>'
        to = ['Łukasz <lukasz.res@gmail.com>']
        message = """From: %s
To: %s
MIME-Version: 1.0
Content-type: text/html
Subject: %s

%s
""" % (sent_from, 'lukasz.res@gmail.com', subject, message)

        server.sendmail(gmail_user, to, message)
        server.close()
        self.logger.info("message sent")
