for f in ../../output/output*; do ffmpeg -i $f -i ../../sounds/track.mp3 -filter_complex 'volume=3[a0];[1:a]volume=0.3[a1];[a0][a1]amix=inputs=2:duration=first' ${f%.*}_audiobook.wav ; done

