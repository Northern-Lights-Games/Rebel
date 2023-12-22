Here's Rebel's backstory :)

## The AWT/Swing Era

Around 2016 or so I started to seriously get into learning to code with Java. I had a copy of Head First Java (a wonderful book by the way) and through struggling with some of the concepts, I'd  managed to piece together some understanding in a graveyard of abandoned projects.

And of course I had no idea how to use GitHub, so I don't have these projects anymore.

Head First Java is a relatively old book. It was written when Swing was just replacing AWT for devices, and the newest kid on the block were the idea of Java Applets (remember those?). Admittedly in 2016 Applets were long dead in the decade before, but the core knowledge of the AWT API was something I stuck with.

For me, making things graphically show up on the screen was the pinnacle of excitement for my younger self. I made quite a lot of simple games like an asteroids clone and probably Walmart Flappy Bird but with Kangaroos.

## The limits of Java2D

Java2D really hit it's performance limit once Swing UIs went out of fashion. AWT could natively use whatever widgets the OS provided, but was not customizable. Swing handled all the rendering by itself, and was incredibly customizable, but was incredibly slow. Both were old.

Now I don't work in games professionally, I've always wanted to make games just because they're fun to make! And to keep that fun alive, I'd been missing a simplistic but modern replacement for the devolving state of Java graphics.

Projects like LITIEngine did a great job of squeezing every last drop of performance out of Java2D, but the AWT/Swing lack of VSync, Shaders, Wayland support (only KDE renders Swing apps sharply right now), not to mention controller support were not restrictions I'd want to live with any longer.

Some time in between, I'd picked up C and had fun with SDL. SDL and Raylib were basically a revelation. They provided a simple graphics API and tools to just throw together a game, and make it fast, 60fps, on every platform. With a bit of longing as to why Java couldn't have something like this, I'd started *daydreaming* about what a fully hardware-accelerated, easy-to-use, and extensible equivalent would look like in Java land.

I'd started learning about LWJGL (an OpenGL binding to Java) and some of the math behind Vectors and Matrices, and implemented a simple renderer a long time back. It wasn't anything fancy, but in hindsight making a simple game with it would've been the best thing to do to really stress test it.

## Rebel

With the holidays approaching I decided to write an OpenGL Batch Renderer that could render a few textured quads. Then I added Text Rendering, Bezier Curves, Mouse/Key Input, customizable shaders and so much more. I picked the name Rebel either because I was rewatching the original Star Wars trilogy or because I was playing Need for Speed too much.



Rebel is meant to be a powerful but simple toolkit for building games. It's meant to pick up after the simplicity of AWT and Swing and to make making games fun again, at least for me. On that note, Rebel can also interface with Java2D via BufferedImage. You can submit BufferedImages to Textures and also use custom AWT fonts with FontRes.

Rebel is written in Java because that's what I started learning to code with. C/C++ have SDL/Raylib (great libraries), but Java is kind of lacking.

Rebel doesn't have an editor at the moment, and I don't really plan to include one. Finally, there are no commercial plans for Rebel. It's under the MIT license and free to use for everyone.

I hope Rebel can be the bridge for someone to make games and learn Java, the way I did, with colorful pixels and wide-eyed wonder.

__Happy Holidays/New Year 2024!__
