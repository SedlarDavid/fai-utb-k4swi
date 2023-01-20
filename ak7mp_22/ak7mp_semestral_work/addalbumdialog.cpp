#include "addalbumdialog.h"
#include "qdir.h"
#include "qformlayout.h"
#include "qpushbutton.h"

AddAlbumDialog::AddAlbumDialog(QWidget *parent) :
    QDialog(parent)
{
    m_nameEdit = new QLineEdit;
    m_performerNameEdit = new QLineEdit;
    m_genreEdit = new QLineEdit;
    m_releaseYearEdit = new QLineEdit;
    m_pickAlbumImage = new QPushButton;
    m_buttonBox = new QDialogButtonBox(QDialogButtonBox::Ok | QDialogButtonBox::Cancel);

    m_pickAlbumImage->setText("Pick image");

    QFormLayout *formLayout = new QFormLayout;
    formLayout->addRow("Name:", m_nameEdit);
    formLayout->addRow("Performer name:", m_performerNameEdit);
    formLayout->addRow("Genre:", m_genreEdit);
    formLayout->addRow("Release year:", m_releaseYearEdit);

    formLayout->addRow( m_pickAlbumImage);

    QVBoxLayout *mainLayout = new QVBoxLayout;
    mainLayout->addLayout(formLayout);
    mainLayout->addWidget(m_buttonBox);

    setLayout(mainLayout);

    connect(m_buttonBox, &QDialogButtonBox::accepted, this, &QDialog::accept);
    connect(m_buttonBox, &QDialogButtonBox::rejected, this, &QDialog::reject);
    connect(m_pickAlbumImage, &QPushButton::clicked, this, &AddAlbumDialog::pickAlbumImage);
}

void AddAlbumDialog::pickAlbumImage(){

    QString fileName = QFileDialog::getOpenFileName(this, tr("Open Image"), "", tr("Image Files (*.png *.jpg *.bmp)"));
    if (!fileName.isEmpty()) {

        QDir dir = QDir::current();
        dir.cd("../ak7mp_semestral_work");
        QString imagePath = dir.absoluteFilePath("images/");

        QString cleanedFileName = fileName.split("/").last().split(".").first();
        QString saveFile = imagePath +cleanedFileName + ".bmp";
        QFile file(fileName);
;

        if (file.exists()) {
            file.copy(saveFile);


            m_imagePath = cleanedFileName;
        }
    }
}

AddAlbumDialog::~AddAlbumDialog()
{
}

QString AddAlbumDialog::getName() const
{
    return m_nameEdit->text();
}
QString AddAlbumDialog::getPerformerName() const
{
    return m_performerNameEdit->text();
}
QString AddAlbumDialog::getGenre() const
{
    return m_genreEdit->text();
}
int AddAlbumDialog::getReleaseYear() const
{
    return m_releaseYearEdit->text().toInt();
}
QString AddAlbumDialog::getImagePath() const
{
    return m_imagePath;
}

