/**
 * Created with IntelliJ IDEA.
 * User: anh
 * Date: 14.10.13
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */



var container, stats;

var camera, scene, renderer;

var mouseX = 0, mouseY = 0;

var windowHalfX = window.innerWidth / 2;
var windowHalfY = window.innerHeight / 2;

var base, body, arm1, arm2, hand,f1,f2,f3,f4,f5,f6;
var robotArm= new Object();

var dummyBody = new THREE.Object3D();
var dummyArm1 = new THREE.Object3D();
var dummyArm2 = new THREE.Object3D();
var dummyHand = new THREE.Object3D();
var dummyF1 = new THREE.Object3D();
var dummyF2 = new THREE.Object3D();
var dummyF3 = new THREE.Object3D();
var dummyF4 = new THREE.Object3D();
var dummyF5 = new THREE.Object3D();
var dummyF6 = new THREE.Object3D();

var oneDegree= (Math.PI / 180);
var last =0;
var currentDutyCycle=0;

var currentAngle = new Array(0,0,0,0);


var dummyArray = [];
var tabValue=0;
var initcodeRobotarm=0;
document.addEventListener( 'keydown', onDocumentKeyDown, false );



function initRobotArm() {

    //container = document.getElementById( 'scence' );
    $(document.body).append("<div id='containerRobotArm' style='width:1200px;height:1000px'></div>")
    container = document.getElementById('containerRobotArm');
    /*container = document.createElement("div");
    document.body.appendChild( container );  */

    camera = new THREE.PerspectiveCamera( 45, window.innerWidth / window.innerHeight, 1, 3000 );
    camera.position.z = 50;
    camera.position.y = 80;
    camera.position.x = 40;

    robotArm.initAngle= new Object();
    robotArm.range1= new Object();
    robotArm.direction= new Object();
    robotArm.currentAngle = new Object();

    robotArm.initAngle.base=2*Math.PI/180;
    robotArm.initAngle.body1=(-35+135)*Math.PI/180;
    robotArm.initAngle.arm1=-(34)*Math.PI/180;
    robotArm.initAngle.arm2=(98)*Math.PI/180;
    robotArm.initAngle.hand=0*Math.PI/180;

    robotArm.range1.base=90*Math.PI/180;
    robotArm.range1.body1=135*Math.PI/180;
    robotArm.range1.arm1=180*Math.PI/180;
    robotArm.range1.arm2=180*Math.PI/180;
    robotArm.range1.hand=42*Math.PI/180;

    robotArm.direction.base=1;
    robotArm.direction.body1=-1;
    robotArm.direction.arm1=-1;
    robotArm.direction.arm2=-1;
    robotArm.direction.hand=1;


    // scene

    scene = new THREE.Scene();
    //scene.position.y=scene.position.y+50;

    //var ambient = new THREE.AmbientLight( 0x101030 );
    //scene.add( ambient );

    //var directionalLight = new THREE.DirectionalLight( 0xffeedd );
    //directionalLight.position.set( 0, 0, 1 ).normalize();
    //scene.add( directionalLight );

    var light = new THREE.PointLight( 0xeeeeee, 1.5 );
    light.position.set( 1000, 1000, 2000 );
    scene.add( light )

    // model

    var debugaxis = function(axisLength){
        //Shorten the vertex function
        function v(x,y,z){
            return new THREE.Vertex(new THREE.Vector3(x,y,z));
        }

        //Create axis (point1, point2, colour)
        function createAxis(p1, p2, color){
            var line, lineGeometry = new THREE.Geometry(),
                lineMat = new THREE.LineBasicMaterial({color: color, lineWidth: 1});
            lineGeometry.vertices.push(p1, p2);
            line = new THREE.Line(lineGeometry, lineMat);
            scene.add(line);
        }

        createAxis(v(-axisLength, 0, 0), v(axisLength, 0, 0), 0xFF0000);
        createAxis(v(0, -axisLength, 0), v(0, axisLength, 0), 0x00FF00);
        createAxis(v(0, 0, -axisLength), v(0, 0, axisLength), 0x0000FF);
    };

    //To use enter the axis length
    /*debugaxis(100);*/


    var loader = new THREE.OBJMTLLoader();
    loader.load( 'models/obj/base.obj', 'models/obj/base.mtl', function ( object ) {
        object.position.x = -1.32;
        object.scale.set(25,25,25);
        //object.position.x = 0.033;
        base=object;
        //base.position.y = -0.239;

        base.add(dummyBody);
        // control body location by moving joint x,y,z

        dummyBody.position.x = +0.033;
        dummyBody.position.y = 0.297;
        dummyBody.control = 'y'; // y axis is controlled
        dummyBody.rotation.y=dummyBody.rotation.y+robotArm.initAngle.base;
        // add all dummy joints to an array so i can control them easier later
        dummyArray[0]=dummyBody;


    });

    loader.load( 'models/obj/body.obj', 'models/obj/body.mtl', function ( object ) {
        body=object;
        object.position.x = -0.033;
        dummyBody.add(body);
        // adding joint for arm1
        dummyBody.add(dummyArm1);

        dummyArm1.position.y = 0.11;
        dummyArm1.control = 'x'; // z axis is controlled
        dummyArm1.rotation.x = dummyArm1.rotation.x+robotArm.initAngle.body1-135*oneDegree;
        dummyArray[1]=dummyArm1;


        //scene.add( base );

    } );

    loader.load( 'models/obj/arm1.obj', 'models/obj/arm1.mtl', function ( object ) {
        arm1=object;
        //body.position.x = +0.328;
        //body.position.z = +0.328;

        dummyArm1.add(arm1);

        // add joint for arm 2
        dummyArm1.add(dummyArm2);
        // these offsets are set manually
        dummyArm2.position.z = -0.28;
        dummyArm2.position.y = 0.755;
        dummyArm2.control = 'x';
        dummyArm2.rotation.x=dummyArm2.rotation.x+robotArm.initAngle.arm1;
        dummyArray[2]=dummyArm2;


        //scene.add( base );

    } );

    loader.load( 'models/obj/arm2.obj', 'models/obj/arm2.mtl', function ( object ) {
        arm2=object;
        //body.position.x = +0.328;
        //body.position.z = +0.328;

        dummyArm2.add(arm2);

        dummyArm2.add(dummyHand);
        // these offsets are set manually
        dummyHand.position.z = 0.614;
        dummyHand.position.y = -0.111;
        dummyHand.control = 'x';
        dummyHand.rotation.x=dummyHand.rotation.x+robotArm.initAngle.arm2;
        dummyArray[3]=dummyHand;


        //scene.add( base );

    } );

    loader.load( 'models/obj/hand/hand.obj', 'models/obj/hand/hand.mtl', function ( object ) {
        hand=object;
        dummyHand.add(hand);

        dummyHand.add(dummyF1);
        // these offsets are set manually
        dummyF1.position.x = 0.004;
        dummyF1.position.z = 0.275;
        dummyF1.control = 'y';
        dummyArray[4]=dummyF1;

        dummyHand.add(dummyF6);

        dummyF6.position.x = 0.086;
        dummyF6.position.z = 0.274;
        dummyF6.control = 'y';

        dummyHand.add(dummyF3);

        dummyF3.position.x = 0.022;
        dummyF3.position.z = 0.378;
        dummyF3.control = 'y';

        dummyHand.add(dummyF4);

        dummyF4.position.x = 0.055;
        dummyF4.position.z = 0.377;
        dummyF4.control = 'y';



    } );

    loader.load( 'models/obj/hand/f3.obj', 'models/obj/hand/f3.mtl', function ( object ) {
        f3=object;
        dummyF3.add(f3);
        //dummyF3.add(dummyF3);

        scene.add( base );

    } );

    loader.load( 'models/obj/hand/f4.obj', 'models/obj/hand/f4.mtl', function ( object ) {
        f4=object;
        dummyF4.add(f4);
        //dummyF4.add(dummyF4);

        scene.add( base );

    } );


    loader.load( 'models/obj/hand/f1.obj', 'models/obj/hand/f1.mtl', function ( object ) {
        f1=object;
        dummyF1.add(f1);

        dummyF1.add(dummyF2);
        // these offsets are set manually
        dummyF2.position.x = -0.107;
        dummyF2.position.z = 0.101;
        dummyF2.control = 'x';
        //dummyArray.push(dummy5);


        //scene.add( base );

    } );

    loader.load( 'models/obj/hand/f2.obj', 'models/obj/hand/f2.mtl', function ( object ) {
        f2=object;
        dummyF2.add(f2);
        //dummyF2.add(dummyF2);

        scene.add( base );

    } );



    loader.load( 'models/obj/hand/f6.obj', 'models/obj/hand/f6.mtl', function ( object ) {
        f6=object;
        dummyF6.add(f6);

        dummyF6.add(dummyF5);
        // these offsets are set manually
        dummyF5.position.x = 0.098;
        dummyF5.position.z = 0.1;
        dummyF5.control = 'x';

        //scene.add( base );

    } );

    loader.load( 'models/obj/hand/f5.obj', 'models/obj/hand/f5.mtl', function ( object ) {
        f5=object;
        dummyF5.add(f5);
        //dummyF5.add(dummyF5);

        scene.add( base );

    } );




    //

    renderer = new THREE.WebGLRenderer();
    renderer.setSize( window.innerWidth, window.innerHeight );
    container.appendChild( renderer.domElement );

    document.addEventListener( 'mousemove', onDocumentMouseMove, false );

    //

    window.addEventListener( 'resize', onWindowResize, false );


}

function onDocumentKeyDown( event ) {

    switch( event.keyCode ) {
        case 73:{
            if (initcodeRobotarm==0){
                initcodeRobotarm++;initRobotArm();animate();
            }
            break;
        }
        case 79:{
            if (initcodeQuadcopter==0){
                initcodeQuadcopter++;initQuadcopter();
            }
            break;
        }
        case 87: toggleJoint(); break; // tab

        case 65: offsetScene(-1,0); break;     //arrow <-
        case 83: offsetScene( 1,0); break;     //arrow ->
        //case 38: offsetScene( 0, -1); break; //arrow /\
        //case 40: offsetScene( 0, 1 ); break; //arrow \/

    }

}

function toggleJoint() {

    if (tabValue === dummyArray.length - 1) {
        tabValue = 0;
    }
    else {
        tabValue++;
    }

}

/**
 *
 * @param offsetX
 * @param offsetY
 */
function offsetScene( offsetX, offsetY ) {

    var mag = 0.01;
    var dutyCycle;
    var initAngle=0;
    var range=0;var direction=1;
    // currently offsetY not used
    switch (tabValue) {
        case 0 :initAngle=robotArm.initAngle.base;
                range=robotArm.range1.base;
                direction=robotArm.direction.base
                break;
        case 1 :initAngle=robotArm.initAngle.body1;
                range=robotArm.range1.body1;
                direction=robotArm.direction.body1;
                break;
        case 2 :initAngle=robotArm.initAngle.arm1;
                range=robotArm.range1.arm1;
                direction=robotArm.direction.arm1;
                break;
        case 3 :initAngle=robotArm.initAngle.arm2;
                range=robotArm.range1.arm2;
                direction=robotArm.direction.arm2;
                break;
        case 4 :initAngle=robotArm.initAngle.hand;
                range=robotArm.range1.hand;
                direction=robotArm.direction.hand;
                break;
    }

    if (dummyArray[tabValue].control === 'y') {

        if ((offsetX<0 && parseFloat(dummyArray[tabValue].rotation.y)-initAngle- oneDegree<-2*oneDegree) || (offsetX>0 && parseFloat(dummyArray[tabValue].rotation.y)-initAngle+ oneDegree>range )){
            offsetX=0;
        }

        if(tabValue !=4) {
            dummyArray[tabValue].rotation.y= (dummyArray[tabValue].rotation.y + oneDegree*offsetX);

        } else {
            dummyArray[tabValue].rotation.y = (dummyArray[tabValue].rotation.y + oneDegree*offsetX);
            dummyF6.rotation.y =  dummyF6.rotation.y - oneDegree*offsetX;
            dummyF2.rotation.y =  dummyF2.rotation.y - oneDegree*offsetX;
            dummyF5.rotation.y =  dummyF5.rotation.y + oneDegree*offsetX;
            dummyF3.rotation.y =  dummyF3.rotation.y + oneDegree*offsetX;
            dummyF4.rotation.y =  dummyF4.rotation.y - oneDegree*offsetX;
        }
        currentAngle[tabValue]+=Math.round(offsetX);


        dutyCycle=0.6 + (dummyArray[tabValue].rotation.y-initAngle)*2/range;

    }
    if (dummyArray[tabValue].control === 'x') {
        if ((direction*offsetX<0 && direction*(parseFloat(dummyArray[tabValue].rotation.x)-initAngle- oneDegree)<-2*oneDegree) || ((direction*offsetX>0 && direction*(parseFloat(dummyArray[tabValue].rotation.x)-initAngle+ oneDegree)>range ))){
            offsetX=0;
        }
        dummyArray[tabValue].rotation.x = (dummyArray[tabValue].rotation.x + oneDegree*offsetX);
        dutyCycle=0.6 + direction*(dummyArray[tabValue].rotation.x-initAngle)*2/range;
    }
    //updateServo([{name: 'ServoID', value: (tabValue+8)}, {name: 'dutyCycle', value: dutyCycle}]);
    updateServo((tabValue+8),dutyCycle);
    currentDutyCycle=dutyCycle;
    //robotStat.update(dummyArray);
}


function onWindowResize() {

    windowHalfX = window.innerWidth / 2;
    windowHalfY = window.innerHeight / 2;

    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();

    renderer.setSize( window.innerWidth, window.innerHeight );

}

function onDocumentMouseMove( event ) {

    mouseX = ( event.clientX - windowHalfX ) / 2;
    mouseY = ( event.clientY - windowHalfY ) / 2;

}

//

function animate() {

    requestAnimationFrame( animate );
    render();

}

function render() {

    //camera.position.x += ( mouseX - camera.position.x ) * .05;
    //camera.position.y += ( - mouseY - camera.position.y ) * .05;

    camera.lookAt( scene.position );

    renderer.render( scene, camera );

}