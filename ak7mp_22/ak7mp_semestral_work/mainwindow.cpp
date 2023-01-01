#include "mainwindow.h"
#include "albumlistitem.h"
#include "ui_mainwindow.h"
#include <QDir>
#include <QDebug>
#include <QObject>

void MainWindow::LoadAlbums()
{
    // Make data

    QStringList splited = QDir::currentPath().split("/");
   QString projectDir = splited[splited.length() - 2] + "ak7mp_semestral_work";
   QDir dir = QDir::current();
   dir.cd("../ak7mp_semestral_work");
    QString databasePath = dir.absoluteFilePath("dbs/SemestralWork.db"/*QString("%1%2").arg(projectDir,"/dbs/SemestralWork.db")*/);

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
AlbumListItem* albumItem = dynamic_cast<AlbumListItem*>(ui->listWidget_2->currentItem());
ui->albumName->setText(albumItem->album().name());ui->albumPerformer->setText(albumItem->album().performerName());ui->albumGenre->setText(albumItem->album().genre());ui->albumReleaseYear->setText(QString::number(albumItem->album().releaseYear()));
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

