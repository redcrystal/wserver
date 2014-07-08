/**
 * Created with IntelliJ IDEA.
 * User: anh
 * Date: 16.10.13
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */

var initcodeQuadcopter=0;


function initQuadcopter(){

    $(document.body).append("<div id='containerQuadcopter' style='	width: 1200px;margin: 10px auto;min-height: 500px;'></div>");
    $('#containerQuadcopter').append('<table><tr><td id = "pos1"></td><td id = "pos2"></td></tr></table>');

    $('#pos1').append('' +
        "<div id='result' style='margin-left: 100px;'>" +
        "</div>");
    $('#pos2').append('' +
        "<div id='details1' style='margin-left: 100px;'>" +
        "</div>");

    $('#result').append('<div id="motor0" class="noUi-target"></div>');
    $('#result').append('<div id="motor1" class="noUi-target"></div>');
    $('#result').append('<div id="motor2" class="noUi-target"></div>');
    $('#result').append('<div id="motor3" class="noUi-target"/>');
    $('#result').append('<div id="allMotor" class="noUi-target"/>');

    function toHex( rgb ){
        var i, hex = "#";
        for(i=0;i<3;i++)
            hex += ('0' + parseInt(rgb[i]).toString(16)).slice(-2);
        return hex.toUpperCase();
    }


// Store the three sliders in their own variables
    var  motor0 = $("#motor0")
        ,motor1 = $("#motor1")
        ,motor2 = $("#motor2")
        ,motor3 = $("#motor3")
        ,allMotor = $("#allMotor");


// Group them together in one variable.
// var sliders = motor0.add([motor1, motor2,motor3]);


// Initialize all sliders with the same options
    motor0.noUiSlider({
        range: [0,300]
        ,start: 300
        ,handles: 1
        ,connect: "lower"
        ,orientation: "vertical"
        ,slide: function(){

            // Use the 'slide' callback to keep to color in sync with
            // the slider values. This callback also fires
            // when the slider is moved by clicking or tapping it.


            $("#result").css( "border-color", toHex([
                motor0.val()
                ,motor1.val()
                ,motor2.val()
            ]));
            for (var i=0;i<4;i++)
            {
                $("#td"+i+"2").html("");
                switch (i){
                    case 0: $("#td02").text(100-Math.round(motor0.val()/3)+"%");break;
                    case 1: $("#td12").text(100-Math.round(motor1.val()/3)+"%");break;
                    case 2: $("#td22").text(100-Math.round(motor2.val()/3)+"%");break;
                    case 3: $("#td32").text(100-Math.round(motor3.val()/3)+"%");break;
                }

            }
        }
    }).change( function () {
            updateQuadcopter(1-(motor0.val()/300)+1,1-(motor1.val()/300)+1,1-(motor2.val()/300)+1,1-(motor3.val()/300)+1);

    });

    motor1.noUiSlider({
        range: [0,300]
        ,start: 300
        ,handles: 1
        ,connect: "lower"
        ,orientation: "vertical"
        ,slide: function(){

            // Use the 'slide' callback to keep to color in sync with
            // the slider values. This callback also fires
            // when the slider is moved by clicking or tapping it.


            $("#result").css( "border-color", toHex([
                motor0.val()

                ,motor1.val()
                ,motor2.val()
            ]));
            for (var i=0;i<4;i++)
            {
                $("#td"+i+"2").html("");
                switch (i){
                    case 0: $("#td02").text(100-Math.round(motor0.val()/3)+"%");break;
                    case 1: $("#td12").text(100-Math.round(motor1.val()/3)+"%");break;
                    case 2: $("#td22").text(100-Math.round(motor2.val()/3)+"%");break;
                    case 3: $("#td32").text(100-Math.round(motor3.val()/3)+"%");break;
                }

            }
        }
    }).change( function () {
            updateQuadcopter(1-(motor0.val()/300)+1,1-(motor1.val()/300)+1,1-(motor2.val()/300)+1,1-(motor3.val()/300)+1);

    });

    motor2.noUiSlider({
        range: [0,300]
        ,start: 300
        ,handles: 1
        ,connect: "lower"
        ,orientation: "vertical"
        ,slide: function(){

            // Use the 'slide' callback to keep to color in sync with
            // the slider values. This callback also fires
            // when the slider is moved by clicking or tapping it.


            $("#result").css( "border-color", toHex([
                motor0.val()

                ,motor1.val()
                ,motor2.val()
            ]));
            for (var i=0;i<4;i++)
            {
                $("#td"+i+"2").html("");
                switch (i){
                    case 0: $("#td02").text(100-Math.round(motor0.val()/3)+"%");break;
                    case 1: $("#td12").text(100-Math.round(motor1.val()/3)+"%");break;
                    case 2: $("#td22").text(100-Math.round(motor2.val()/3)+"%");break;
                    case 3: $("#td32").text(100-Math.round(motor3.val()/3)+"%");break;
                }

            }
        }
    }).change( function () {
            updateQuadcopter(1-(motor0.val()/300)+1,1-(motor1.val()/300)+1,1-(motor2.val()/300)+1,1-(motor3.val()/300)+1);

    });

    motor3.noUiSlider({
        range: [0,300]
        ,start: 300
        ,handles: 1
        ,connect: "lower"
        ,orientation: "vertical"
        ,slide: function(){

            // Use the 'slide' callback to keep to color in sync with
            // the slider values. This callback also fires
            // when the slider is moved by clicking or tapping it.


            $("#result").css( "border-color", toHex([
                motor0.val()

                ,motor1.val()
                ,motor2.val()
            ]));
            for (var i=0;i<4;i++)
            {
                $("#td"+i+"2").html("");
                switch (i){
                    case 0: $("#td02").text(100-Math.round(motor0.val()/3)+"%");break;
                    case 1: $("#td12").text(100-Math.round(motor1.val()/3)+"%");break;
                    case 2: $("#td22").text(100-Math.round(motor2.val()/3)+"%");break;
                    case 3: $("#td32").text(100-Math.round(motor3.val()/3)+"%");break;
                }

            }
        }
    }).change( function () {
            updateQuadcopter(1-(motor0.val()/300)+1,1-(motor1.val()/300)+1,1-(motor2.val()/300)+1,1-(motor3.val()/300)+1);

    });

    allMotor.noUiSlider({
        range: [0,300]
        ,start: 300
        ,handles: 1
        ,connect: "lower"
        ,orientation: "vertical"
        ,slide: function(){
            motor0.val(allMotor.val());
            motor1.val(allMotor.val());
            motor2.val(allMotor.val());
            motor3.val(allMotor.val());
            $("#result").css( "border-color", toHex([
                motor0.val()
                ,motor1.val()
                ,motor2.val()
            ]));
            for (var i=0;i<4;i++)
            {
                $("#td"+i+"2").html("");
                switch (i){
                    case 0: $("#td02").text(100-Math.round(motor0.val()/3)+"%");break;
                    case 1: $("#td12").text(100-Math.round(motor1.val()/3)+"%");break;
                    case 2: $("#td22").text(100-Math.round(motor2.val()/3)+"%");break;
                    case 3: $("#td32").text(100-Math.round(motor3.val()/3)+"%");break;
                }

            }
        }
    }).change( function () {
            motor0.val(allMotor.val());
            motor1.val(allMotor.val());
            motor2.val(allMotor.val());
            motor3.val(allMotor.val());
            updateQuadcopter(1-(motor0.val()/300)+1,1-(motor1.val()/300)+1,1-(motor2.val()/300)+1,1-(motor3.val()/300)+1);

    });

    $('#details1').append('<table class="zebra">'
        + '    <thead>'
        + '    <tr>'
        + '       <th>#</th>'
        + '       <th>Name</th>'
        + '        <th>Value</th>'


        + '    </tr>'
        + '        </thead>'
        + '<tfoot>'
        + '    <tr>'
        + '         <td>&nbsp;</td>'
        + '         <td></td>'
        + '         <td></td>'
        + '      </tr>'
        + '   </tfoot>'

        + '   <tr>'
        + '     <td id = "td00"><div style="background-color: #C0392B;width: 10px;height: 10px"/></td>'
        + '     <td id = "td01">Motor 0</td>'
        + '     <td id = "td02"></td>'
        + '  </tr>'

        + '   <tr>'
        + '     <td id = "td10"><div style="background-color: #27AE60;width: 10px;height: 10px"/></td>'
        + '     <td id = "td11">Motor 1</td>'
        + '     <td id = "td12"></td>'
        + '  </tr>'

        + '   <tr>'
        + '     <td id = "td20"><div style="background-color: #2980B9;width: 10px;height: 10px"/></td>'
        + '     <td id = "td21">Motor 2</td>'
        + '     <td id = "td22"></td>'
        + '  </tr>'

        + '   <tr>'
        + '     <td id = "td30"><div style="background-color: #b9b119;width: 10px;height: 10px"/></td>'
        + '     <td id = "td31">Motor 3</td>'
        + '     <td id = "td32"></td>'
        + '  </tr>'

        + '</table>'

    );




}
