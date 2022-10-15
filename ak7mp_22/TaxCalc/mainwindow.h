#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QTranslator>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void on_btnClear_clicked();
    void taxEvaluate();


    void on_radTwentyOne_clicked();

    void on_radFifteen_clicked();
    void on_actionCzech_triggered();

    void on_actionEnglish_triggered();

private:
    Ui::MainWindow *ui;
    QTranslator translator;
};
#endif // MAINWINDOW_H
