#include "mainwindow.h"

#include <QApplication>
#include <QDir>


int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    w.show();

    QString databasePath = QDir::current().absoluteFilePath("dbs/SemestralWork.db");

    // Open a connection to the SQLite database
    QSqlDatabase db = QSqlDatabase::addDatabase("QSQLITE");
    db.setDatabaseName(databasePath);
    if (!db.open()) {
        // Handle error
        return 1;
    }

    // Execute a SELECT statement to retrieve the data
    QSqlQuery query("SELECT id, album_id, name FROM Songs");

    // Iterate over the result set and print the values
    while (query.next()) {
        int id = query.value(0).toInt();
        QString name = query.value(2).toString();
        qDebug() << "id:" << id << "name:" << name;
    }

    // Close the database connection
    db.close();

    return a.exec();
}
