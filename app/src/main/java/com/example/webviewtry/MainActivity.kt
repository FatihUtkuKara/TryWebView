package com.example.webviewtry

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.webView)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true // JavaScript desteğini etkinleştir
        webSettings.domStorageEnabled = true

        webSettings.setSupportZoom(false)
        webSettings.builtInZoomControls = false
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE

        webView.webChromeClient = WebChromeClient()


        webView.loadUrl("file:///android_asset/index3.html")
    }


    private fun getHtmlData(): String {
        return """
           <!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  </script>

  <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/ammo.js/2.0.0/ammo.js"></script> -->

  <script src="https://cdn.jsdelivr.net/npm/three@0.138.3/build/three.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/three@0.138.3/examples/js/loaders/GLTFLoader.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/three@0.122.0/examples/js/controls/OrbitControls.min.js"></script>

  <script src="https://cdn.jsdelivr.net/npm/three@0.122.0/examples/js/libs/stats.min.js"></script>

  <style>
    body {
      /* background-image: url(https://ca.slack-edge.com/T01P4J7D52S-U02NJ074EN9-fb2d02b7758d-512); */
      margin: 0;
      overflow: hidden;
    }

    canvas {
      display: block;
      background-color: transparent;
    }
  </style>

</head>

<body>
  <script>

    let stats;
    let camera, scene, raycaster, renderer;

    let INTERSECTED;
    let theta = 0;

    const pointer = new THREE.Vector2();
    const radius = 5;

    init();
    animate();

    function init() {

      camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 0.1, 100);

      scene = new THREE.Scene();
      scene.background = new THREE.Color(0xf0f0f0);

      const light = new THREE.DirectionalLight(0xffffff, 3);
      light.position.set(1, 1, 1).normalize();
      scene.add(light);

      const geometry = new THREE.BoxGeometry();

      for (let i = 0; i < 2000; i++) {

        const object = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({ color: Math.random() * 0xffffff }));

        object.position.x = Math.random() * 40 - 20;
        object.position.y = Math.random() * 40 - 20;
        object.position.z = Math.random() * 40 - 20;

        object.rotation.x = Math.random() * 2 * Math.PI;
        object.rotation.y = Math.random() * 2 * Math.PI;
        object.rotation.z = Math.random() * 2 * Math.PI;

        object.scale.x = Math.random() + 0.5;
        object.scale.y = Math.random() + 0.5;
        object.scale.z = Math.random() + 0.5;

        scene.add(object);

      }

      raycaster = new THREE.Raycaster();

      renderer = new THREE.WebGLRenderer({ antialias: true });
      renderer.setPixelRatio(window.devicePixelRatio);
      renderer.setSize(window.innerWidth, window.innerHeight);
      document.body.appendChild(renderer.domElement);

      stats = new Stats();
      document.body.appendChild(stats.dom);

      document.addEventListener('mousemove', onPointerMove);

      //

      window.addEventListener('resize', onWindowResize);

    }

    function onWindowResize() {

      camera.aspect = window.innerWidth / window.innerHeight;
      camera.updateProjectionMatrix();

      renderer.setSize(window.innerWidth, window.innerHeight);

    }

    function onPointerMove(event) {

      pointer.x = (event.clientX / window.innerWidth) * 2 - 1;
      pointer.y = - (event.clientY / window.innerHeight) * 2 + 1;

    }

    //

    function animate() {

      requestAnimationFrame(animate);

      render();
      stats.update();

    }

    function render() {

      theta += 0.1;

      camera.position.x = radius * Math.sin(THREE.MathUtils.degToRad(theta));
      camera.position.y = radius * Math.sin(THREE.MathUtils.degToRad(theta));
      camera.position.z = radius * Math.cos(THREE.MathUtils.degToRad(theta));
      camera.lookAt(scene.position);

      camera.updateMatrixWorld();

      // find intersections

      raycaster.setFromCamera(pointer, camera);

      const intersects = raycaster.intersectObjects(scene.children, false);

      if (intersects.length > 0) {

        if (INTERSECTED != intersects[0].object) {

          if (INTERSECTED) INTERSECTED.material.emissive.setHex(INTERSECTED.currentHex);

          INTERSECTED = intersects[0].object;
          INTERSECTED.currentHex = INTERSECTED.material.emissive.getHex();
          INTERSECTED.material.emissive.setHex(0xff0000);

        }

      } else {

        if (INTERSECTED) INTERSECTED.material.emissive.setHex(INTERSECTED.currentHex);

        INTERSECTED = null;

      }

      renderer.render(scene, camera);

    }
  </script>


</body>

</html>
        """
    }
    }
