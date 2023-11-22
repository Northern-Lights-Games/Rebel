#version 330 core
out vec4 FragColor;

in vec2 f_texcoord;

uniform sampler2D u_textures[32];
in float f_texindex;
in vec4 f_color;

void main()
{
    int index = int(f_texindex);


    if(index == -1){
        FragColor = f_color;
    }
    else {
        FragColor = texture(u_textures[index], f_texcoord) * f_color;
    }

}
