#version 330 core
out vec4 FragColor;

in vec2 f_uv;

uniform sampler2D u_textures[32];
in float f_texindex;
in vec4 f_color;
in vec2 f_origin;
in vec2 f_size;
uniform float myT;
//gl_FragCoord goes y-axis up, but Rebel uses y-axis down...
layout(origin_upper_left) in vec4 gl_FragCoord;

struct Line {
    vec2 start;
    vec2 end;
};



vec2 tValue(Line line, float t){
    return vec2(line.start.x + ((line.end.x - line.start.x) * t), line.start.y + ((line.end.y - line.start.y) * t));
}

vec2 quadraticBezier(vec2 p0, vec2 p1, vec2 p2, float t){


    Line a = Line(p0, p1);
    Line b = Line(a.end, p2);
    Line c = Line(vec2(0, 0), vec2(0, 0));


    c.start = tValue(a, t);
    c.end = tValue(b, t);
    vec2 tracer = tValue(c, t);

    return tracer;
}

void main() {


    for(float i = 0.0; i < myT; i+=0.01f){

        vec2 point = quadraticBezier(vec2(150, 150), vec2(250, 250), vec2(300, 150), i);

        if(distance(gl_FragCoord.xy , point) < 5){
            FragColor = vec4(1.0, 0.0, 0.0, 1.0);
            return;
        }
    }

    discard;

}