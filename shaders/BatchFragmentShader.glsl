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
    else if(index == -2){

        //gl_FragCoord is in pixel coordinates
        //the circle shader needs NDC [-1 <-> 1]





        vec2 uv = vec2(gl_FragCoord.x / (640 * 1.5), gl_FragCoord.y / (480 * 1.5)) * 2.0 - 1.0;

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
