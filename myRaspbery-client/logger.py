import logging
import os
from logging.handlers import TimedRotatingFileHandler


class Logger:
    def createLogsDirectory(self, path):
        if not os.path.exists(path):
            os.makedirs(path)

    def getLogger(self, loggerName):
        logPath = os.getcwd() + "//logs"
        rootLogger = logging.getLogger(loggerName)
        self.createLogsDirectory(logPath)
        logFormatter = logging.Formatter("%(asctime)s [%(threadName)-12.12s] [%(levelname)-4.8s]  %(message)s")
        fileName = loggerName
        fileHandler = logging.FileHandler("{0}/{1}.log".format(logPath, fileName))
        fileHandler.setFormatter(logFormatter)
        timeHandler = TimedRotatingFileHandler(logPath + "//common.log", when="w0", interval=1, backupCount=5)
        rootLogger.addHandler(fileHandler)
        rootLogger.addHandler(timeHandler)

        consoleHandler = logging.StreamHandler()
        consoleHandler.setFormatter(logFormatter)
        rootLogger.addHandler(consoleHandler)
        rootLogger.setLevel(logging.INFO)
        return rootLogger
