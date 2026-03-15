form DetectNuclei
    sentence wavfile
    sentence tgfile
endform

Read from file... 'wavfile$'
sound = selected("Sound")

# Intensity
selectObject: sound
To Intensity... 50 0 yes
intensity = selected("Intensity")

# Create TextGrid with dummy interval tier, then add point tier "nuclei"
selectObject: sound
dur = Get total duration
Create TextGrid... 0 dur "dummy"
tg = selected("TextGrid")
selectObject: tg
Insert point tier... 2 "nuclei"

# Parameters (tune later)
thrAdd = 2.0    ; dB above median
dipMin = 2.0    ; required dip before peak
backWin = 0.20  ; seconds to look back

# Work on intensity frames
selectObject: intensity
n = Get number of frames
tmin = Get start time
dt = Get time step

# Median intensity threshold
median = Get quantile... 0 0 0.5
threshold = median + thrAdd

for i from 2 to n - 1
    t = tmin + (i - 1) * dt
    x = Get value in frame... i
    prev = Get value in frame... (i - 1)
    next = Get value in frame... (i + 1)

    # Local maximum + above threshold
    if x > prev and x >= next and x > threshold

        # Find minimum intensity in the last backWin seconds (no 'break' used)
        minVal = 1e9
        j = i
        goOn = 1

        while goOn = 1
            tt = tmin + (j - 1) * dt
            # Stop if we have looked back enough OR hit start
            if (t - tt) > backWin
                goOn = 0
            elsif j <= 1
                goOn = 0
            else
                v = Get value in frame... j
                if v < minVal
                    minVal = v
                endif
                j = j - 1
            endif
        endwhile

        # Dip requirement
        if x - minVal >= dipMin
            selectObject: tg
            Insert point... 2 t "n"
            selectObject: intensity
        endif
    endif
endfor

selectObject: tg
Write to text file... 'tgfile$'
