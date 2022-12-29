#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QSettings>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    QSettings sett;
    double tax = sett.value("general/tax",21.0).toDouble();
    if(tax == 15.0){
        ui->radFifteen->setChecked(true);
        ui->radTwentyOne->setChecked(false);
    }else if (tax == 21.0) {
        ui->radFifteen->setChecked(false);
        ui->radTwentyOne->setChecked(true);}


    QString lang= sett.value("general/language","eng").toString();
    if(lang == "eng"){
        ui->actionEnglish->setChecked(true);
        ui->actionCzech->setChecked(false);
        translator.load(":/lang/trans_eng.qm");
    }else if (lang == "cze") {
        ui->actionCzech->setChecked(true);
        ui->actionEnglish->setChecked(false);
        translator.load(":/lang/trans_cze.qm");
}

    qApp->installTranslator(&translator);
    ui->retranslateUi(this);

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

    ui->leWithTax->setText(tr("%1 EUR").arg(value));
}else{
    ui->leWithTax->setText("N/A");

}
}


void MainWindow::on_radTwentyOne_clicked()
{
    QSettings sett;
    sett.setValue("general/tax", 21.0);
}


void MainWindow::on_radFifteen_clicked()
{
    QSettings sett;
    sett.setValue("general/tax", 15.0);

}

void MainWindow::on_actionCzech_triggered()
{

    QSettings sett;
    sett.setValue("general/language", "cze");
    ui->actionEnglish->setChecked(false);

    qApp->removeTranslator(&translator);
    translator.load(":/lang/trans_cze.qm");
    qApp->installTranslator(&translator);
    ui->retranslateUi(this);
}


void MainWindow::on_actionEnglish_triggered()
{

    QSettings sett;
    sett.setValue("general/language", "eng");
    ui->actionCzech->setChecked(false);

    qApp->removeTranslator(&translator);
    translator.load(":/lang/trans_eng.qm");
    qApp->installTranslator(&translator);
    ui->retranslateUi(this);
}

