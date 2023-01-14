#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "qlistwidget.h"
#include <QSqlDatabase>
#include <QSqlQuery>
#include <QMainWindow>
#include "album.h"
#include "albumlistitem.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

    void SetUpMenu();
    void LoadAlbums();
    void OnAlbumChanged();
    void OnSearchChanged(const QString query);
    void OnImagePicker();
    void DisplayAlbumCover(const Album &album);

private:
    Ui::MainWindow *ui;
    QList<Album> albumList;

     QAction *editAlbumAction;
     QAction *saveAlbumChangesAction;

     void OnAddNewAlbum();
     void OnEditAlbum();
     void OnSaveAlbumChanges();

     void SwitchEditAlbumFields (bool isEdit);
     AlbumListItem* MapAlbumToListItem(const Album &album);
};
#endif // MAINWINDOW_H
