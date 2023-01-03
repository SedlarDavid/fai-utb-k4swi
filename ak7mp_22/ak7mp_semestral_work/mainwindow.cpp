#include "mainwindow.h"
#include "albumlistitem.h"
#include "ui_mainwindow.h"
#include <QDir>
#include <QDebug>
#include <QObject>
#include <QMenuBar>
#include <QtWidgets>
#include <QSqlQuery>


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
    editAlbumAction->setEnabled(true);
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

void MainWindow::SetUpMenu(){
    // Create the menu bar and add it to the main window
    QMenuBar *menuBar = new QMenuBar(this);
    this->setMenuBar(menuBar);

    QMenu *fileMenu = new QMenu("File", menuBar);
    menuBar->addMenu(fileMenu);
    QMenu *editMenu = new QMenu("Edit", menuBar);
    menuBar->addMenu(editMenu);

    //File
    QAction *newAlbumAction = new QAction("New album", fileMenu);
    newAlbumAction->setShortcut(QKeySequence("Ctrl+N"));
    fileMenu->addAction(newAlbumAction);
    fileMenu->addSeparator();

    //Edit
    editAlbumAction = new QAction("Edit album", fileMenu);
    editAlbumAction->setShortcut(QKeySequence("Ctrl+E"));
    editAlbumAction->setDisabled(true);
    editMenu->addAction(editAlbumAction);
    saveAlbumChangesAction = new QAction("Save changes", fileMenu);
    saveAlbumChangesAction->setShortcut(QKeySequence("Ctrl+S"));
    saveAlbumChangesAction->setDisabled(true);
    editMenu->addAction(saveAlbumChangesAction);

    QObject::connect(newAlbumAction, &QAction::triggered, this, &MainWindow::OnAddNewAlbum);
    QObject::connect(editAlbumAction, &QAction::triggered, this, &MainWindow::OnEditAlbum);
    QObject::connect(saveAlbumChangesAction, &QAction::triggered, this, &MainWindow::OnSaveAlbumChanges);
}

void MainWindow::OnAddNewAlbum(){}
void MainWindow::OnEditAlbum(){
    saveAlbumChangesAction->setDisabled(false);

    AlbumListItem* albumItem = dynamic_cast<AlbumListItem*>(ui->listWidget_2->currentItem());


    ui->albumNameEdit->setText(albumItem->album().name());
    ui->albumPerformerEdit->setText(albumItem->album().performerName());
    ui->albumGenreEdit->setText(albumItem->album().genre());
    ui->albumYearEdit->setText(QString::number(albumItem->album().releaseYear()));

    SwitchEditAlbumFields(true);


}

void MainWindow::SwitchEditAlbumFields (bool isEdit){
    ui->albumName->setVisible(!isEdit);
    ui->albumPerformer->setVisible(!isEdit);
    ui->albumGenre->setVisible(!isEdit);
    ui->albumReleaseYear->setVisible(!isEdit);

    ui->albumNameEdit->setVisible(isEdit);
    ui->albumPerformerEdit->setVisible(isEdit);
    ui->albumGenreEdit->setVisible(isEdit);
    ui->albumYearEdit->setVisible(isEdit);
}

void MainWindow::OnSaveAlbumChanges(){

    saveAlbumChangesAction->setDisabled(true);

    AlbumListItem* albumItem = dynamic_cast<AlbumListItem*>(ui->listWidget_2->currentItem());

    QString sql = "UPDATE Albums SET name = :name, performer_name = :performer_name, genre = :genre, release_year = :release_year,  WHERE id = :id";


    // Execute the UPDATE statement
    QSqlQuery query;
    query.prepare(sql);

   QString name =  ui->albumNameEdit->text();
   QString performer_name =  ui->albumPerformerEdit->text();
   QString genre =  ui->albumGenreEdit->text();
   int release_year = ui->albumNameEdit->text().toInt();
   int id = albumItem->album().id();

    query.bindValue(":name",name);
    query.bindValue(":performer_name",performer_name);
    query.bindValue(":genre",genre);
    query.bindValue(":release_year",release_year);
    query.bindValue(":id",id);

    if (!query.exec()) {
        qDebug() << "Error: Could not update item";
    }

    SwitchEditAlbumFields(false);

    ui->albumName->setText(name);
    ui->albumPerformer->setText(performer_name);
    ui->albumGenre->setText(genre);
    ui->albumReleaseYear->setText(QString::number(release_year));

}

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    ui->albumNameEdit->setVisible(false);
    ui->albumPerformerEdit->setVisible(false);
    ui->albumGenreEdit->setVisible(false);
    ui->albumYearEdit->setVisible(false);

    ui->albumImage->setFrameShape(QFrame::Box);
    ui->albumImage->setLineWidth(1);


    SetUpMenu();

    LoadAlbums();


    connect(ui->listWidget_2,&QListWidget::itemSelectionChanged,this,&MainWindow::OnAlbumChanged);

    connect(ui->inputSearch,&QLineEdit::textChanged,this,&MainWindow::OnSearchChanged);

}

MainWindow::~MainWindow()
{
    delete ui;
}

