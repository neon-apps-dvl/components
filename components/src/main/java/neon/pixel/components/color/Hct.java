package neon.pixel.components.color;

import androidx.annotation.FloatRange;

public class Hct {
    private float hue;
    private float chroma;
    private float tone;

    public Hct (float hue, @FloatRange (from = 0f, to = 1f) float chroma, @FloatRange (from = 0f, to = 100f) float tone) {
        this.hue = hue;
        this.chroma = chroma;
        this.tone = tone;
    }

    public static Hct fromInt (int argb) {
        Cam16 cam = Cam16.fromInt (argb);
        return new Hct (cam.getHue (), cam.getChroma (), ColorUtils.lstarFromInt (argb));
    }

    public float getHue () {
        return hue;
    }

    public float getChroma () {
        return chroma;
    }

    public float getTone () {
        return tone;
    }

    public int toInt () {
        return gamutMap (hue, chroma, tone);
    }


    public void setHue (float newHue) {
        setInternalState (gamutMap (MathUtils.sanitizeDegrees (newHue), chroma, tone));
    }


    public void setChroma (float newChroma) {
        setInternalState (gamutMap (hue, newChroma, tone));
    }


    public void setTone (float newTone) {
        setInternalState (gamutMap (hue, chroma, newTone));
    }

    private void setInternalState (int argb) {
        Cam16 cam = Cam16.fromInt (argb);
        float tone = ColorUtils.lstarFromInt (argb);
        hue = cam.getHue ();
        chroma = cam.getChroma ();
        this.tone = tone;
    }


    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;


    private static final float DE_MAX = 1.0f;


    private static final float DL_MAX = 0.2f;


    private static final float DE_MAX_ERROR = 0.000000001f;


    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;


    private static int gamutMap (float hue, float chroma, float tone) {
        return gamutMapInViewingConditions (hue, chroma, tone, ViewingConditions.DEFAULT);
    }


    static int gamutMapInViewingConditions (
            float hue, float chroma, float tone, ViewingConditions viewingConditions) {

        if (chroma < 1.0 || Math.round (tone) <= 0.0 || Math.round (tone) >= 100.0) {
            return ColorUtils.intFromLstar (tone);
        }

        hue = MathUtils.sanitizeDegrees (hue);

        float high = chroma;
        float mid = chroma;
        float low = 0.0f;
        boolean isFirstLoop = true;

        Cam16 answer = null;
        while (Math.abs (low - high) >= CHROMA_SEARCH_ENDPOINT) {
            Cam16 possibleAnswer = findCamByJ (hue, mid, tone);

            if (isFirstLoop) {
                if (possibleAnswer != null) {
                    return possibleAnswer.viewed (viewingConditions);
                } else {
                    isFirstLoop = false;
                    mid = low + (high - low) / 2.0f;
                    continue;
                }
            }

            if (possibleAnswer == null) {
                high = mid;
            } else {
                answer = possibleAnswer;
                low = mid;
            }

            mid = low + (high - low) / 2.0f;
        }

        if (answer == null) {
            return ColorUtils.intFromLstar (tone);
        }

        return answer.viewed (viewingConditions);
    }


    private static Cam16 findCamByJ (float hue, float chroma, float tone) {
        float low = 0.0f;
        float high = 100.0f;
        float mid = 0.0f;
        float bestdL = 1000.0f;
        float bestdE = 1000.0f;

        Cam16 bestCam = null;
        while (Math.abs (low - high) > LIGHTNESS_SEARCH_ENDPOINT) {
            mid = low + (high - low) / 2;
            Cam16 camBeforeClip = Cam16.fromJch (mid, chroma, hue);
            int clipped = camBeforeClip.getInt ();
            float clippedLstar = ColorUtils.lstarFromInt (clipped);
            float dL = Math.abs (tone - clippedLstar);

            if (dL < DL_MAX) {
                Cam16 camClipped = Cam16.fromInt (clipped);
                float dE =
                        camClipped.distance (Cam16.fromJch (camClipped.getJ (), camClipped.getChroma (), hue));
                if (dE <= DE_MAX && dE <= bestdE) {
                    bestdL = dL;
                    bestdE = dE;
                    bestCam = camClipped;
                }
            }

            if (bestdL == 0 && bestdE < DE_MAX_ERROR) {
                break;
            }

            if (clippedLstar < tone) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return bestCam;
    }
}
