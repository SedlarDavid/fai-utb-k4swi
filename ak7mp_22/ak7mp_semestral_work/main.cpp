#include "mainwindow.h"

#include <QApplication>

#include <QDir>


int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    // Make data
    QDir dir = QDir::current();
    dir.cd("../ak7mp_semestral_work");
    QString databasePath = dir.absoluteFilePath("dbs/SemestralWork.db");

    // Open a connection to the SQLite database
    QSqlDatabase db = QSqlDatabase::addDatabase("QSQLITE");
    db.setDatabaseName(databasePath);
    if (!db.open()) {
        // Handle error
        //  return qFatal("Database not opened!");
    }


    MainWindow w;
    w.show();

    return a.exec();
}
