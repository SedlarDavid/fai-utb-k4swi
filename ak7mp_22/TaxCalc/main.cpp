#include "mainwindow.h"

#include <QApplication>
#include <QFile>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    a.setApplicationName("TaxCalculator");
    a.setOrganizationName("UTB");
    a.setOrganizationDomain("utb.cz");


    QFile file(":/styles/style.qss");
    if(file.open(QIODevice::ReadOnly | QIODevice::Text)){
        a.setStyleSheet(file.readAll());
        file.close();
    }


    MainWindow w;
    w.show();
    return a.exec();
}
