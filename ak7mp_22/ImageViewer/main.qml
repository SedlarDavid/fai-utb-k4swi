import QtQuick 2.15
import QtQuick.Window 2.15
import QtQuick.Controls 2.15
import QtQuick.Controls.Material 2.15
import QtQuick.Layouts 1.15

ApplicationWindow {
    width: 600
    height: 800
    visible: true
    title: qsTr("Image Viewer")


    Material.primary: 'steelblue'
    Material.background: 'lightgray'

    header: ToolBar{
        id: mainToolbar

        Material.foreground: 'white'

        RowLayout{
            anchors.fill: parent

            ToolButton{

                contentItem: Image{
                    source: "qrc:/img/menu.png"
                    fillMode: Image.Pad
                }

                onClicked: mainDrawer.open()

            }


            Label{
                text:qsTr("Viewer")
                Layout.fillWidth: true
                horizontalAlignment: Qt.AlignHCenter
                font.pointSize: 14
                font.bold: true

            }

            ToolButton{

                contentItem: Image{
                    source: "qrc:/img/settings.png"
                    fillMode: Image.Pad
                }
                onClicked: mainMenu.open()

                Menu{id: mainMenu
                    y:mainToolbar.height
                    MenuItem{
                        text: qsTr ("Quit")

                        onTriggered: Qt.quit()
                    }

                }
            }
        }
    }

    Drawer{
        id:mainDrawer
        width:parent.width *0.6
        height:parent.height

        ListView{
            id:imgSelector
            anchors.fill: parent
            anchors.margins: 20
            spacing: 20
            focus:true

            model:imgModel

            delegate:

                Column{
                width : parent.width

                spacing:5
                Image{
                    width:parent.width
                    source: model.url
                    fillMode: Image.PreserveAspectFit

                    MouseArea {
                    anchors.fill: parent
                    onClicked: imgSelector.currentIndex = model.index
                    }
                }

                Label{
                    text:model.name
                    anchors.horizontalCenter: parent.horizontalCenter
                    font.bold: (imgSelector.currentIndex === model.index)
                }


            }

        }

    }

    Image{
    id:photo
    anchors.fill: parent
    anchors.margins: 20
fillMode: Image.PreserveAspectFit
source: imgModel[imgSelector.currentIndex].url
    }

   /* ListModel {
        id: imgModel
        ListElement {
            name: "Penguin"
            url:"qrc:/img/peng.jpg"
        }    ListElement {
            name: "Kitten"
            url:"qrc:/img/kitten.jpg"
        }    ListElement {
            name: "Sloth"
            url:"qrc:/img/sloth.jpg"
        }

    }*/

}
