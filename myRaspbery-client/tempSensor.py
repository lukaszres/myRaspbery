from time import sleep

from emailSender import EmailSender
from gpiozero import Button
from logger import Logger

button17 = Button(17, pull_up=True)
button27 = Button(27, pull_up=True)
button22 = Button(22, pull_up=True)
# logger = Logger()
# commonLogger = logger.getLogger("common")
#
# messages = ["Puste wiaderko", "1/2 wiaderka", "3/4 wiaderka", "Pelne wiaderko"]
# counter = 0
# while True:
#     if button17.is_pressed:
#         if counter != 1:
#             counter = 1
#         if button27.is_pressed:
#             if counter != 2:
#                 counter = 2
#             if button22.is_pressed:
#                 if counter != 3:
#                     counter = 3
#     else:
#         if counter != 0:
#             counter = 0
#
#     print(messages[counter])
#     logger.info(messages[counter])
#
#     sleep(1)


button17is_pressed = True
button27is_pressed = False
button22is_pressed = True

logger = Logger()
commonLogger = logger.getLogger("common")

emailSender = EmailSender(commonLogger)

messages = ["Puste wiaderko", "1/2 wiaderka", "3/4 wiaderka", "Pelne wiaderko"]
status = 0
changed = True


def send(message_number):
    commonLogger.warning(messages[message_number])
    emailSender.send("TempSensors", messages[message_number])


while True:
    if button17.is_pressed:
        if status != 1:
            status = 1
            send(status)
        if button27.is_pressed:
            if status != 2:
                status = 2
                send(status)
            if button22.is_pressed:
                if status != 3:
                    status = 3
                    send(status)
    else:
        if status != 0:
            status = 0
            send(status)
    sleep(300)
