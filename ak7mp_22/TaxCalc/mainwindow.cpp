#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    connect(ui->btnEval,&QPushButton::clicked,this,&MainWindow::taxEvaluate);
    connect(ui->radFifteen,&QRadioButton::clicked,this,&MainWindow::taxEvaluate);
    connect(ui->radTwentyOne,&QRadioButton::clicked,this,&MainWindow::taxEvaluate);
    connect(ui->leWithoutTax,&QLineEdit::textChanged,this,&MainWindow::taxEvaluate);
}

MainWindow::~MainWindow()
{
    delete ui;
}


void MainWindow::on_btnClear_clicked()
{
    ui->leWithoutTax->clear();
    ui->leWithTax->clear();
}

void MainWindow::taxEvaluate()
{
bool success;
double value = ui->leWithoutTax->text().toDouble(&success);
if(success){
    if(ui->radFifteen->isChecked()){
        value *= 1.15;
    }else if (ui->radTwentyOne->isChecked()){
        value *= 1.21;
    }

    ui->leWithTax->setText(QString("%1 EUR").arg(value));
}else{
    ui->leWithTax->setText("N/A");

}
}

