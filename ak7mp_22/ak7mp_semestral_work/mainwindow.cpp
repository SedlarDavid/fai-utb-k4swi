#include "mainwindow.h"
#include "albumlistitem.h"
#include "ui_mainwindow.h"
#include <QDir>
#include <QDebug>


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
        QString performerName = query.value(2).toString();
        QString genre = query.value(3).toString();
        QString img = query.value(4).toString();
        int releaseYear = query.value(5).toInt();
        Album album = Album(id, name, performerName, genre, img, releaseYear);
        ui->listWidget_2->addItem(new AlbumListItem(album, ui->listWidget_2));
    }

    // Close the database connection
    db.close();
}

void MainWindow::OnAlbumChanged(){
    qDebug() << ui->listWidget_2->currentItem()->text();
}

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    LoadAlbums();


    connect(ui->listWidget_2,&QListWidget::itemSelectionChanged,this,&MainWindow::OnAlbumChanged);

}

MainWindow::~MainWindow()
{
    delete ui;
}

