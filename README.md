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

Rebel is a Gradle project, which means you will have to download the source from GitHub and run the Main class in demo. I use IntelliJ, but any IDE that supports Gradle will work.

![demo.png](project%2Fdemo.png)