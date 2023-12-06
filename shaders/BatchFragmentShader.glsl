#version 330 core
out vec4 FragColor;

in vec2 f_texcoord;

uniform sampler2D u_textures[32];
in float f_texindex;
in vec4 f_color;
in vec2 f_origin;
in vec2 f_size;
//gl_FragCoord goes y-axis up, but Rebel uses y-axis down...
layout(origin_upper_left) in vec4 gl_FragCoord;


void main()
{
    int index = int(f_texindex);


    if(index == -1){
        FragColor = f_color;
    }
    else if(index == -2){

        vec2 uv = vec2((gl_FragCoord.x - f_origin.x) / f_size.x, (gl_FragCoord.y - f_origin.y) / f_size.y) * 2.0 - 1.0;

        float distance = 1 - length(uv);

        if(distance > 0.0)
            FragColor = f_color;
        else {
            discard;
        }

    }
    else {
        FragColor = texture(u_textures[index], f_texcoord) * f_color;
    }

}
