#include "mainwindow.h"
#include <QMenu>
#include <QMenuBar>
#include <QAction>

#include <QString>
#include <QFileDialog>
#include <QFile>
#include <QTextStream>
#include <QMessageBox>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
{
    setGeometry(100,100,800,600);
    QMenu *fileMenu = new QMenu("File");

    menuBar()->addMenu(fileMenu);

    QAction *actionOpen = new QAction("Open txt file");
    actionOpen->setShortcut(QKeySequence::Open);

    QAction *actionSave = new QAction("Save file as");
    actionSave->setShortcut(QKeySequence::Save);

    QAction *actionQuit = new QAction("Quit");
    actionQuit->setShortcut(QKeySequence::Quit);


    fileMenu->addAction(actionOpen);
    fileMenu->addAction(actionSave);
    fileMenu->addSeparator();
    fileMenu->addAction(actionQuit);


    m_memo = new QTextEdit();
    setCentralWidget(m_memo);

    connect(actionQuit, &QAction::triggered, this, &MainWindow::close);
    connect(actionSave, &QAction::triggered, this, &MainWindow::saveTextFile);
    connect(actionOpen, &QAction::triggered, this, &MainWindow::openTextFile);

}

MainWindow::~MainWindow()
{
}

void MainWindow::openTextFile()
{
    QString filePath = QFileDialog::getOpenFileName(this,"Simple Notepad","","Text file (*.txt)");

    if(!filePath.isEmpty()){
        QFile file(filePath);
        if(file.open(QIODevice::ReadOnly | QIODevice::Text)){
            QTextStream out(&file);
            m_memo->setText(out.readAll());
            file.close();
        }else{
            QMessageBox::critical(this, "Simple Notepad", "File cannot be opened!" );
        }
    }else{
        QMessageBox::critical(this, "Simple Notepad", "File path must be provided!" );
    }
}

void MainWindow::saveTextFile()
{

    QString filePath = QFileDialog::getSaveFileName(this,"Simple Notepad","","Text file (*.txt)");

    if(!filePath.isEmpty()){
        QFile file(filePath);
        if(file.open(QIODevice::WriteOnly | QIODevice::Text)){
            QTextStream in(&file);
            in << m_memo->toPlainText();
            file.close();
        }else{
            QMessageBox::critical(this, "Simple Notepad", "File cannot be opened!" );
        }
    }else{
        QMessageBox::critical(this, "Simple Notepad", "File path must be provided!" );
    }
}

