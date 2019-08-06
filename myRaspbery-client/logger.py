import logging
import os


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
        rootLogger.addHandler(fileHandler)

        consoleHandler = logging.StreamHandler()
        consoleHandler.setFormatter(logFormatter)
        rootLogger.addHandler(consoleHandler)
        rootLogger.setLevel(logging.INFO)
        return rootLogger


# logger = Logger()
# commonLogger = logger.getLogger("common")
# commonLogger.debug('This is a debug message')
# commonLogger.info('This is an info message')
# commonLogger.warning('This is a warning message')
# commonLogger.error('This is an error message')
# commonLogger.critical('This is a critical message')
