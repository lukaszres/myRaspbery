from gpiozero import Button
from time import sleep

button = Button(17, pull_up=True)

while True:
    if button.is_pressed:
        print("Pressed")
    else:
        print("Released")
    sleep(1)