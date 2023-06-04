#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QThread>

namespace Ui {
class MainWindow;
}

class Worker : public QObject {
    Q_OBJECT

public:
    explicit Worker(QObject *parent = nullptr);

public slots:
    void calculateFactorial(int number);
    void calculatePrimes(int range);

signals:
    void resultReady(QString result);
    void progressChanged(int value);
};

class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void on_factorialButton_clicked();
    void on_primeButton_clicked();
    void on_cancelButton_clicked();
    void updateResult(QString result);
    void updateProgress(int value);

private:
    Ui::MainWindow *ui;
    QThread *workerThread;
    Worker *worker;
};

#endif // MAINWINDOW_H
