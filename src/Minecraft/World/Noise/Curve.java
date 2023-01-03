package Minecraft.World.Noise;

import java.util.ArrayList;
import java.util.List;

public class Curve {
    private final List<Keyframe> curve;

    public Curve(Keyframe[] keyframes)
    {
        curve = new ArrayList<Keyframe>(keyframes.length);

        for (Keyframe keyframe : keyframes) {
            curve.add(keyframe);
        }
    }

    public int Evaluate(float time)
    {
        for(int i = 0; i < curve.size(); i++)
        {
            Keyframe keyframe = curve.get(i);
            if(keyframe.Time < time)
                continue;

            if(curve.size() == 1 || keyframe.Time == time)
                return keyframe.Value;

            Keyframe pKeyframe = curve.get(i-1);

            int valueDelta = keyframe.Value - pKeyframe.Value;
            float timeDelta = keyframe.Time - pKeyframe.Time;
            float time2Delta = time - pKeyframe.Time;

            return pKeyframe.Value + Math.round((time2Delta / timeDelta) * valueDelta);
        }

        return 0;
    }
}
