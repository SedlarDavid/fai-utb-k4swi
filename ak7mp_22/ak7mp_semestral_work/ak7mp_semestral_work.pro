QT       += core gui sql

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += c++17

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
    addalbumdialog.cpp \
    album.cpp \
    albumlistitem.cpp \
    main.cpp \
    mainwindow.cpp

HEADERS += \
    addalbumdialog.h \
    album.h \
    albumlistitem.h \
    mainwindow.h

FORMS += \
    mainwindow.ui

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target

DISTFILES += \
    dbs/SemestralWork.db \
    dbs/dbs.sqlite \
    dbs/init_db.cmd \
    images/pucina.bmp \
    images/rebels_never_die.bmp \
    images/rebels_never_die.jpg \
    images/united_we_are.bmp \
    images/united_we_are.jpg
