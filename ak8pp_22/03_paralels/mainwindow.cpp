#include "mainwindow.h"
#include <iostream>
#include <omp.h>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
{
    limitedButton = new QPushButton("Run Limited Algorithm", this);
    limitedButton->setGeometry(50, 50, 200, 50);
    connect(limitedButton, SIGNAL(clicked()), this, SLOT(runLimitedAlgorithm()));

    scalableButton = new QPushButton("Run Scalable Algorithm", this);
    scalableButton->setGeometry(50, 120, 200, 50);
    connect(scalableButton, SIGNAL(clicked()), this, SLOT(runScalableAlgorithm()));

    outputText = new QTextEdit(this);
       outputText->setGeometry(50, 190, 300, 200);
       outputText->setReadOnly(true);

       int width = 400;
        int height = 450;
        setFixedSize(width, height);
}

MainWindow::~MainWindow()
{
}

void limitedParallelPrefixSum(int* input, int size) {
    #pragma omp parallel for
    for (int i = 1; i < size; i++) {
        input[i] = input[i] + input[i - 1];
    }
}

void scalableParallelPrefixSum(int* input, int size, int numThreads) {
    omp_set_num_threads(numThreads);
    int* temp = new int[size];

    #pragma omp parallel
    {
        int threadID = omp_get_thread_num();
        int numThreads = omp_get_num_threads();
        int blockSize = (size + numThreads - 1) / numThreads;

        int start = threadID * blockSize;
        int end = std::min(start + blockSize, size);

        for (int i = start + 1; i < end; i++) {
            input[i] = input[i] + input[i - 1];
        }

        if (end > 0) {
            temp[threadID] = input[end - 1];
        }

        #pragma omp barrier

        if (threadID > 0) {
            int prefixSum = 0;
            for (int i = 0; i < threadID; i++) {
                prefixSum += temp[i];
            }

            for (int i = start; i < end; i++) {
                input[i] += prefixSum;
            }
        }
    }

    delete[] temp;
}

void MainWindow::runLimitedAlgorithm()
{
    const int maxSize = 32;
    int input[maxSize];

    for (int i = 0; i < maxSize; i++) {
        input[i] = i + 1;
    }

    limitedParallelPrefixSum(input, maxSize);

    QString result;
    for (int i = 0; i < maxSize; i++) {
        result += QString::number(input[i]) + " ";
    }
    outputText->setText(result);
}

void MainWindow::runScalableAlgorithm()
{
    bool ok;
    int size = QInputDialog::getInt(this, "Input Size", "Enter the size of the input array:", 1, 1, 1000000, 1, &ok);
    if (!ok) return;

    int numThreads = QInputDialog::getInt(this, "Number of Threads", "Enter the number of active threads (max 32):", 1, 1, 32, 1, &ok);
    if (!ok) return;

    int* input = new int[size];

    for (int i = 0; i < size; i++) {
        input[i] = i + 1;
    }

    scalableParallelPrefixSum(input, size, numThreads);

    QString result;
    for (int i = 0; i < size; i++) {
        result += QString::number(input[i]) + " ";
    }
    outputText->setText(result);

    delete[] input;
}
