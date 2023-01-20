#ifndef ADDALBUMDIALOG_H
#define ADDALBUMDIALOG_H

#include "qdialog.h"
#include "qdialogbuttonbox.h"
#include "qlineedit.h"
#include "qspinbox.h"
#include "qwidget.h"
#include <QFileDialog>


class AddAlbumDialog : public QDialog
{
    Q_OBJECT

public:
    AddAlbumDialog(QWidget *parent = nullptr);
    ~AddAlbumDialog();

    QString getName() const;
    QString getPerformerName() const;
    QString getGenre() const;
    int getReleaseYear() const;
    QString getImagePath() const;

private:
    QLineEdit *m_nameEdit;
    QLineEdit *m_performerNameEdit;
    QLineEdit *m_genreEdit;
    QLineEdit *m_releaseYearEdit;
    QPushButton *m_pickAlbumImage;
    QString m_imagePath;
    QDialogButtonBox *m_buttonBox;
    void pickAlbumImage();
};
#endif // ADDALBUMDIALOG_H
