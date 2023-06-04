#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QDebug>

Worker::Worker(QObject *parent) : QObject(parent) {}

void Worker::calculateFactorial(int number) {
    long long result = 1;

    for (int i = 2; i <= number; ++i) {
        result *= i;

        if (i % 10000 == 0 || i == number) {
            emit progressChanged(i * 100 / number);
        }

        if (QThread::currentThread()->isInterruptionRequested()) {
            emit progressChanged(0);
            return;
        }
    }

    emit progressChanged(0);
    emit resultReady(QString::number(result));
}

void Worker::calculatePrimes(int range) {
    QVector<bool> primes(range + 1, true);
    primes[0] = primes[1] = false;

    for (int p = 2; p * p <= range; ++p) {
        if (primes[p]) {
            for (int i = p * p; i <= range; i += p) {
                primes[i] = false;
            }
        }

        if (QThread::currentThread()->isInterruptionRequested()) {
            emit progressChanged(0);
            return;
        }
    }

    QStringList primeList;
    for (int i = 0; i <= range; ++i) {
        if (primes[i]) {
            primeList.append(QString::number(i));
        }

        if (i % 1000 == 0 || i == range) {
            emit progressChanged(i * 100 / range);
        }

        if (QThread::currentThread()->isInterruptionRequested()) {
            emit progressChanged(0);
            return;
        }
    }

    emit progressChanged(0);
    emit resultReady(primeList.join(", "));
}


MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow) {
    ui->setupUi(this);

    workerThread = new QThread(this);
    worker = new Worker();

    worker->moveToThread(workerThread);

    connect(worker, SIGNAL(resultReady(QString)), this, SLOT(updateResult(QString)));
    connect(worker, SIGNAL(progressChanged(int)), this, SLOT(updateProgress(int)));
    connect(workerThread, SIGNAL(finished()), worker, SLOT(deleteLater()));
    connect(workerThread, SIGNAL(finished()), workerThread, SLOT(deleteLater()));

    ui->progressBar->setValue(0);

    workerThread->start();
}


MainWindow::~MainWindow() {
    workerThread->quit();
    workerThread->wait();
    delete ui;
}

void MainWindow::on_factorialButton_clicked() {
    int number = ui->numberSpinBox->value();
    ui->resultLabel->setText("Calculating factorial...");

    QMetaObject::invokeMethod(worker, "calculateFactorial", Qt::QueuedConnection, Q_ARG(int, number));
}

void MainWindow::on_primeButton_clicked() {
    int range = ui->rangeSpinBox->value();
    ui->resultLabel->setText("Calculating primes...");

    QMetaObject::invokeMethod(worker, "calculatePrimes", Qt::QueuedConnection, Q_ARG(int, range));
}

void MainWindow::on_cancelButton_clicked() {
    workerThread->requestInterruption();
    workerThread->quit();
    workerThread->wait();
    ui->resultLabel->setText("Calculation canceled.");
}

void MainWindow::updateResult(QString result) {
    ui->resultLabel->setText(result);
}

void MainWindow::updateProgress(int value) {
    ui->progressBar->setValue(value);
}
