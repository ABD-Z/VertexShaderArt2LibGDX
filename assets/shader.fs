#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying float v_distance;

void main() {
    gl_FragColor = vec4(0.0, 1.0 - v_distance , 1.5 - v_distance, 0.5);
}
