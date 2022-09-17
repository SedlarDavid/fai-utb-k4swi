#include <QApplication>
#include <iostream>
#include <QPushButton>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QPushButton button("Click me!");

    button.setGeometry(100,100,300,200);
    button.show();

    QObject::connect(&button, &QPushButton::clicked, &a, [](){
        std::cout << "Bye, bye!" << std::endl;
        QApplication::quit();
    });

    return a.exec();
}
