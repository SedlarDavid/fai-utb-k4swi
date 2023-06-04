#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QPushButton>
#include <QInputDialog>
#include <QTextEdit>

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void runLimitedAlgorithm();
    void runScalableAlgorithm();

private:
    QPushButton *limitedButton;
    QPushButton *scalableButton;
    QTextEdit *outputText;
};

#endif // MAINWINDOW_H
