#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QDir>
#include <QDebug>
#include <QObject>
#include <QMenuBar>
#include <QtWidgets>
#include <QSqlQuery>
#include <QList>


void MainWindow::LoadAlbums()
{

    ui->listWidget_2->clear();

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

    QString imageName = album.name().toLower().replace(" ", "_") + ".bmp";

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

AlbumListItem* MainWindow::MapAlbumToListItem(const Album &album)
{
    return new AlbumListItem(album, ui->listWidget_2);
}

bool filterAlbum(const Album &album, QString &query)
{
    return !album.name().contains(query,Qt::CaseInsensitive)
            &&  !album.genre().contains(query,Qt::CaseInsensitive)
            && !QString::number(album.releaseYear()).contains(query,Qt::CaseInsensitive)  ;
}
void MainWindow::OnSearchChanged(const QString query){
    ui->listWidget_2->clear();

    if(query.isEmpty())
    {
        QList<AlbumListItem*> albumListItemsList;
        albumListItemsList.resize(albumList.size());
        std::function<AlbumListItem*(const Album &album)> mapper = std::bind(&MainWindow::MapAlbumToListItem, this, std::placeholders::_1);
        std::transform(albumList.begin(), albumList.end(), albumListItemsList.begin(), mapper);
        for (int i = 0; i < albumListItemsList.count(); i++) {
            ui->listWidget_2->addItem(albumListItemsList[i]);
        }
    }else{
        QList<Album> workingList(albumList);

        workingList.erase(std::remove_if(workingList.begin(), workingList.end(),
                                         std::bind(filterAlbum, std::placeholders::_1, query)),
                          workingList.end());

        QList<AlbumListItem*> albumListItemsList;
        albumListItemsList.resize(workingList.size());

        std::function<AlbumListItem*(const Album &album)> mapper = std::bind(&MainWindow::MapAlbumToListItem, this, std::placeholders::_1);
        std::transform(workingList.begin(), workingList.end(), albumListItemsList.begin(), mapper);
        for (int i = 0; i < albumListItemsList.count(); i++) {
            ui->listWidget_2->addItem(albumListItemsList[i]);
        }
    }
}

void MainWindow::OnImagePicker(){
    QString fileName = QFileDialog::getOpenFileName(this, tr("Open Image"), "", tr("Image Files (*.png *.jpg *.bmp)"));
    if (!fileName.isEmpty()) {

        QDir dir = QDir::current();
        dir.cd("../ak7mp_semestral_work");
        QString imagePath = dir.absoluteFilePath("images/");

        AlbumListItem* albumItem = dynamic_cast<AlbumListItem*>(ui->listWidget_2->currentItem());
        QString saveFile = imagePath +albumItem->album().image() + ".bmp"; // create the full path to the save file, including the desired file name and extension
        QFile file(fileName); // create a QFile object using the selected file's path
    QFile oldFile(saveFile);

    oldFile.remove();

        if (file.exists()) {
            file.copy(saveFile);

            if (file.error() == QFile::NoError) {

                QPixmap image(saveFile);

                ui->albumImage->setPixmap(image);
                ui->albumImage->setScaledContents(true);
            } else {
                // an error occurred while saving the file
            }
        }
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
    ui->pickImage->setVisible(isEdit);

    ui->albumNameEdit->setVisible(isEdit);
    ui->albumPerformerEdit->setVisible(isEdit);
    ui->albumGenreEdit->setVisible(isEdit);
    ui->albumYearEdit->setVisible(isEdit);
}

void MainWindow::OnSaveAlbumChanges(){

    saveAlbumChangesAction->setDisabled(true);

    AlbumListItem* albumItem = dynamic_cast<AlbumListItem*>(ui->listWidget_2->currentItem());

    QString sql = "UPDATE Albums SET name = :name, performer_name = :performer_name, genre = :genre, release_year = :release_year  WHERE id = :id";


    // Execute the UPDATE statement
    QSqlQuery query;
    query.prepare(sql);

    QString name =  ui->albumNameEdit->text();
    QString performer_name =  ui->albumPerformerEdit->text();
    QString genre =  ui->albumGenreEdit->text();
    int release_year = ui->albumYearEdit->text().toInt();
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

    int row =
            ui->listWidget_2->currentRow();
    LoadAlbums();
    ui->listWidget_2->setCurrentRow(row);
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
    ui->pickImage->setVisible(false);

    ui->albumImage->setFrameShape(QFrame::Box);
    ui->albumImage->setLineWidth(1);


    SetUpMenu();

    LoadAlbums();


    connect(ui->listWidget_2,&QListWidget::itemSelectionChanged,this,&MainWindow::OnAlbumChanged);

    connect(ui->inputSearch,&QLineEdit::textChanged,this,&MainWindow::OnSearchChanged);

    connect(ui->pickImage,&QPushButton::clicked,this,&MainWindow::OnImagePicker);
}

MainWindow::~MainWindow()
{
    delete ui;
}

