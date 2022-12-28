#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "qlistwidget.h"
#include <QSqlDatabase>
#include <QSqlQuery>
#include <QMainWindow>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

    void LoadAlbums();
    void OnAlbumChanged();

private:
    Ui::MainWindow *ui;
};
#endif // MAINWINDOW_H
