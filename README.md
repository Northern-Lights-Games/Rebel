# Rebel - 2D Java Game Engine

<img src="project/logo.png" alt="isolated" width="250"/>

Rebel is a WIP Java Game Engine for making 2D games on Desktop. It provides an OpenGL Batch Renderer that can draw textures, shapes and text. Rebel is open-source under the MIT license, so join the fun and let's build together!

## Features (Rebel 2.0)

- 2D Primitive Batch Rendering (Rectangles/Circles/Lines/Textures)
- Sprite-sheet based Animation
- Text rendering with different fonts
- Input handling (keys/mouse)
- Quadratic/Cubic Bezier curve API
- Full GPU GLSL implementation of a Cubic Bezier
- Compatibility with Java2D, (BufferedImages can be loaded to Textures)
- Simple transformation API including rotate() and scale() methods that abstract matrix math
- Simple Particle System API (ParticleSource)
- Custom GLSL shader support (Vertex/Fragment Shaders)
- Packages into a single .jar file to include in your classpath!
- Control over your main loop, create a Window, Renderer2D and your own application loop

## Getting Started

Rebel is configured using Gradle and it's recommended to clone the repository using Git and run the _demo.ShowcaseDemo_ file. 
The current Rebel release cycle will see major changes between releases, and the API will be quite volatile. 

## Gallery


![Image description](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/7cfi2z8n5yo7vkfq8jj5.png)

*The animated character is Zara, a spritesheet I made for a game but never really used. Maybe she's the mascot of Rebel?*

Here's a demo of some GPU Bezier Curves I made using custom GLSL shaders. This isn't part of Rebel just yet, but Rebel can render CPU-calculated Cubic/Quadratic Bezier Curves.


![Image description](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/dx8p2u0a9cm01g1al9o7.png)

Rebel's Particle System (ParticleSource):


![Image description](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/cfgcju73d9r09dq55sn0.png)



![demo.png](project%2Fdemo.png)