#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QDir>

void MainWindow::LoadAlbums()
{
    // Make data
    QString databasePath = QDir::current().absoluteFilePath("dbs/SemestralWork.db");

    // Open a connection to the SQLite database
    QSqlDatabase db = QSqlDatabase::addDatabase("QSQLITE");
    db.setDatabaseName(databasePath);
    if (!db.open()) {
        // Handle error
      //  return qFatal("Database not opened!");
    }

    // Execute a SELECT statement to retrieve the data
    QSqlQuery query("SELECT id, name, performer_name, genre, img, release_year FROM Albums");

    // Iterate over the result set and print the values
    while (query.next()) {
        int id = query.value(0).toInt();
        QString name = query.value(1).toString();
        ui->listWidget_2->addItem(name);
    }

    // Close the database connection
    db.close();
}

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);


    LoadAlbums();

}

MainWindow::~MainWindow()
{
    delete ui;
}

