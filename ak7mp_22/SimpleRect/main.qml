import QtQuick 2.15
import QtQuick.Window 2.15

Window {
    width: 640
    height: 480
    visible: true
    title: qsTr("Hello World")

    Rectangle {
    id: myRect
    x:100
    y:100
    width:100
    height:100
    color:"lightblue"
    border.color: "black"
    border.width: 2
    radius: 10

    focus: true
    Keys.onUpPressed: y -= 10
    Keys.onDownPressed: y += 10
    Keys.onLeftPressed: x -= 10
    Keys.onRightPressed: x += 10

    Behavior on x {
        NumberAnimation {
        duration: 1000
        easing.type: Easing.OutElastic
        }
    }
    Behavior on y {
        NumberAnimation {
        duration: 1000
        easing.type: Easing.OutElastic
        }
    }


    Text{
        anchors.centerIn: parent
    text: qsTr("Click me...")
    }

    MouseArea{
    anchors.fill: parent
    onClicked: {
   myRect.x = Math.random() * (myRect.parent.width - myRect.width)
        myRect.y = Math.random() * (myRect.parent.height - myRect.height)

        }
    }

    }
}
