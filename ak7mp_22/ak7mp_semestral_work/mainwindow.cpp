#include "mainwindow.h"
#include "albumlistitem.h"
#include "ui_mainwindow.h"
#include <QDir>
#include <QDebug>
#include <QObject>

void MainWindow::LoadAlbums()
{

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
        albumList.append(album);
        ui->listWidget_2->addItem(new AlbumListItem(album, ui->listWidget_2));
    }

}

void MainWindow::DisplayAlbumCover(const Album &album){

    QString imageName = album.name().toLower().replace(" ", "_") + ".jpg";

    QDir dir = QDir::current();
    dir.cd("../ak7mp_semestral_work");
    QString imagePath = dir.absoluteFilePath("images/") + imageName;

     QPixmap image(imagePath);

     ui->albumImage->setPixmap(image);
     ui->albumImage->setScaledContents(true);
}

void MainWindow::OnAlbumChanged(){
    ui->albumSongs->clear();

    AlbumListItem* albumItem = dynamic_cast<AlbumListItem*>(ui->listWidget_2->currentItem());
    ui->albumName->setText(albumItem->album().name());
    ui->albumPerformer->setText(albumItem->album().performerName());
    ui->albumGenre->setText(albumItem->album().genre());
    ui->albumReleaseYear->setText(QString::number(albumItem->album().releaseYear()));
    DisplayAlbumCover(albumItem->album());

    QSqlQuery query;
    query.prepare("SELECT name FROM Songs WHERE album_id LIKE :id");
    query.bindValue(":id", "%" + QString::number(albumItem->album().id()) + "%");
    query.exec();

    while (query.next()) {
        QString name = query.value(0).toString();
        ui->albumSongs->addItem(name);
    }

}

QString mapAlbumToName(const Album &album)
{
    return album.name();
}

bool filterByName(const Album &album, QString &query)
{
    return !album.name().contains(query,Qt::CaseInsensitive);
}
void MainWindow::OnSearchChanged(const QString query){
    ui->listWidget_2->clear();

    if(query.isEmpty())
    {
        QList<QString> nameList;
        nameList.resize(albumList.size());
        std::transform(albumList.begin(), albumList.end(), nameList.begin(), mapAlbumToName);
        ui->listWidget_2->addItems(nameList);

    }else{
        QList<Album> workingList(albumList);

        workingList.erase(std::remove_if(workingList.begin(), workingList.end(),
                                         std::bind(filterByName, std::placeholders::_1, query)),
                          workingList.end());

        QList<QString> nameList;
        nameList.resize(workingList.size());
        std::transform(workingList.begin(), workingList.end(), nameList.begin(), mapAlbumToName);
        ui->listWidget_2->addItems(nameList);
    }
}

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    ui->albumImage->setFrameShape(QFrame::Box);
    ui->albumImage->setLineWidth(1);


    LoadAlbums();


    connect(ui->listWidget_2,&QListWidget::itemSelectionChanged,this,&MainWindow::OnAlbumChanged);

    connect(ui->inputSearch,&QLineEdit::textChanged,this,&MainWindow::OnSearchChanged);

}

MainWindow::~MainWindow()
{
    delete ui;
}

