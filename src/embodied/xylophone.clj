(ns embodied.xylophone
  (:require [midi.soundfont :refer [load-all-instruments! midi-test load-patch]]
            [midi.soundfont.fluid-r3 :as fluid-r3]
            [clojure.string :as str])
  (:import (javax.sound.midi MidiSystem)))

(defonce synth (MidiSystem/getSynthesizer))
(defonce first-channel (first (.getChannels synth)))
(defonce _ (load-all-instruments! synth fluid-r3/sf2))

(def instruments
  [{:family "Piano", :instrument "Acoustic Grand Piano", :number 0}
   {:family "Piano", :instrument "Bright Acoustic Piano", :number 1}
   {:family "Piano", :instrument "Electric Grand Piano", :number 2}
   {:family "Piano", :instrument "Honky-tonk Piano", :number 3}
   {:family "Piano", :instrument "Electric Piano 1", :number 4}
   {:family "Piano", :instrument "Electric Piano 2", :number 5}
   {:family "Piano", :instrument "Harpsichord", :number 6}
   {:family "Piano", :instrument "Clavichord", :number 7}
   {:family "Chromatic Percussion", :instrument "Celesta", :number 8}
   {:family "Chromatic Percussion", :instrument "Glockenspiel", :number 9}
   {:family "Chromatic Percussion", :instrument "Music Box", :number 10}
   {:family "Chromatic Percussion", :instrument "Vibraphone", :number 11}
   {:family "Chromatic Percussion", :instrument "Marimba", :number 12}
   {:family "Chromatic Percussion", :instrument "Xylophone", :number 13}
   {:family "Chromatic Percussion", :instrument "Tubular bells", :number 14}
   {:family "Chromatic Percussion", :instrument "Dulcimer", :number 15}
   {:family "Organ", :instrument "Drawbar Organ", :number 16}
   {:family "Organ", :instrument "Percussive Organ", :number 17}
   {:family "Organ", :instrument "Rock Organ", :number 18}
   {:family "Organ", :instrument "Church Organ", :number 19}
   {:family "Organ", :instrument "Reed Organ", :number 20}
   {:family "Organ", :instrument "Accordion", :number 21}
   {:family "Organ", :instrument "Harmonica", :number 22}
   {:family "Organ", :instrument "Tango Accordion", :number 23}
   {:family "Guitar", :instrument "Acoustic Guitar (nylon)", :number 24}
   {:family "Guitar", :instrument "Acoustic Guitar (steel)", :number 25}
   {:family "Guitar", :instrument "Electric Guitar (jazz)", :number 26}
   {:family "Guitar", :instrument "Electric Guitar (clean)", :number 27}
   {:family "Guitar", :instrument "Electric Guitar (muted)", :number 28}
   {:family "Guitar", :instrument "Overdriven Guitar", :number 29}
   {:family "Guitar", :instrument "Distortion Guitar", :number 30}
   {:family "Guitar", :instrument "Guitar harmonics", :number 31}
   {:family "Bass", :instrument "Acoustic Bass", :number 32}
   {:family "Bass", :instrument "Electric Bass (finger)", :number 33}
   {:family "Bass", :instrument "Electric Bass (pick)", :number 34}
   {:family "Bass", :instrument "Fretless Bass", :number 35}
   {:family "Bass", :instrument "Slap Bass 1", :number 36}
   {:family "Bass", :instrument "Slap bass 2", :number 37}
   {:family "Bass", :instrument "Synth Bass 1", :number 38}
   {:family "Bass", :instrument "Synth Bass 2", :number 39}
   {:family "Strings", :instrument "Violin", :number 40}
   {:family "Strings", :instrument "Viola", :number 41}
   {:family "Strings", :instrument "Cello", :number 42}
   {:family "Strings", :instrument "Contrabass", :number 43}
   {:family "Strings", :instrument "Tremolo Strings", :number 44}
   {:family "Strings", :instrument "Pizzicato Strings", :number 45}
   {:family "Strings", :instrument "Orchestral Harp", :number 46}
   {:family "Strings", :instrument "Timpani", :number 47}
   {:family "Ensemble", :instrument "String Ensemble 1", :number 48}
   {:family "Ensemble", :instrument "String Ensemble 2", :number 49}
   {:family "Ensemble", :instrument "SynthStrings 1", :number 50}
   {:family "Ensemble", :instrument "SynthStrings 2", :number 51}
   {:family "Ensemble", :instrument "Choir Aahs", :number 52}
   {:family "Ensemble", :instrument "Voice Oohs", :number 53}
   {:family "Ensemble", :instrument "Synth Voice", :number 54}
   {:family "Ensemble", :instrument "Orchestra Hit", :number 55}
   {:family "Brass", :instrument "Trumpet", :number 56}
   {:family "Brass", :instrument "Trombone", :number 57}
   {:family "Brass", :instrument "Tuba", :number 58}
   {:family "Brass", :instrument "Muted Trombone", :number 59}
   {:family "Brass", :instrument "French Horn", :number 60}
   {:family "Brass", :instrument "Brass Section", :number 61}
   {:family "Brass", :instrument "SynthBrass 1", :number 62}
   {:family "Brass", :instrument "SynthBrass 2", :number 63}
   {:family "Reed", :instrument "Soprano Sax", :number 64}
   {:family "Reed", :instrument "Alto Sax", :number 65}
   {:family "Reed", :instrument "Tenor Sax", :number 66}
   {:family "Reed", :instrument "Baritone Sax", :number 67}
   {:family "Reed", :instrument "Oboe", :number 68}
   {:family "Reed", :instrument "English Horn", :number 69}
   {:family "Reed", :instrument "Bassoon", :number 70}
   {:family "Reed", :instrument "Clarinet", :number 71}
   {:family "Pipe", :instrument "Piccolo", :number 72}
   {:family "Pipe", :instrument "Flute", :number 73}
   {:family "Pipe", :instrument "Recorder", :number 74}
   {:family "Pipe", :instrument "Pan Flute", :number 75}
   {:family "Pipe", :instrument "Blown Bottle", :number 76}
   {:family "Pipe", :instrument "Shakuhachi", :number 77}
   {:family "Pipe", :instrument "Whistle", :number 78}
   {:family "Pipe", :instrument "Ocarina", :number 79}
   {:family "Synth Lead", :instrument "Lead 1 (square)", :number 80}
   {:family "Synth Lead", :instrument "Lead 2 (sawtooth)", :number 81}
   {:family "Synth Lead", :instrument "Lead 3 (calliope)", :number 82}
   {:family "Synth Lead", :instrument "Lead 4 (chiff)", :number 83}
   {:family "Synth Lead", :instrument "Lead 5 (charang)", :number 84}
   {:family "Synth Lead", :instrument "Lead 6 (voice)", :number 85}
   {:family "Synth Lead", :instrument "Lead 7 (fifths)", :number 86}
   {:family "Synth Lead", :instrument "Lead 8 (bass + lead)", :number 87}
   {:family "Synth Pad", :instrument "Pad 1 (new age)", :number 88}
   {:family "Synth Pad", :instrument "Pad 2 (warm)", :number 89}
   {:family "Synth Pad", :instrument "Pad 3 (polysynth)", :number 90}
   {:family "Synth Pad", :instrument "Pad 4 (choir)", :number 91}
   {:family "Synth Pad", :instrument "Pad 5 (bowed)", :number 92}
   {:family "Synth Pad", :instrument "Pad 6 (metallic)", :number 93}
   {:family "Synth Pad", :instrument "Pad 7 (halo)", :number 94}
   {:family "Synth Pad", :instrument "Pad 8 (sweep)", :number 95}
   {:family "Synth Effects", :instrument "FX 1 (rain)", :number 96}
   {:family "Synth Effects", :instrument "FX 2 (soundtrack)", :number 97}
   {:family "Synth Effects", :instrument "FX 3 (crystal)", :number 98}
   {:family "Synth Effects", :instrument "FX 4 (atmosphere)", :number 99}
   {:family "Synth Effects", :instrument "FX 5 (brightness)", :number 100}
   {:family "Synth Effects", :instrument "FX 6 (goblins)", :number 101}
   {:family "Synth Effects", :instrument "FX 7 (echoes)", :number 102}
   {:family "Synth Effects", :instrument "FX 8 (sci-fi)", :number 103}
   {:family "Ethnic", :instrument "Sitar", :number 104}
   {:family "Ethnic", :instrument "Banjo", :number 105}
   {:family "Ethnic", :instrument "Shamisen", :number 106}
   {:family "Ethnic", :instrument "Koto", :number 107}
   {:family "Ethnic", :instrument "Kalimba", :number 108}
   {:family "Ethnic", :instrument "Bag pipe", :number 109}
   {:family "Ethnic", :instrument "Fiddle", :number 110}
   {:family "Ethnic", :instrument "Shanai", :number 111}
   {:family "Percussive", :instrument "Tinkle Bell", :number 112}
   {:family "Percussive", :instrument "Agogo", :number 113}
   {:family "Percussive", :instrument "Steel Drums", :number 114}
   {:family "Percussive", :instrument "Woodblock", :number 115}
   {:family "Percussive", :instrument "Taiko Drum", :number 116}
   {:family "Percussive", :instrument "Melodic Tom", :number 117}
   {:family "Percussive", :instrument "Synth Drum", :number 118}
   {:family "Percussive", :instrument "Reverse Cymbal", :number 119}
   {:family "Sound Effects", :instrument "Guitar Fret Noise", :number 120}
   {:family "Sound Effects", :instrument "Breath Noise", :number 121}
   {:family "Sound Effects", :instrument "Seashore", :number 122}
   {:family "Sound Effects", :instrument "Bird Tweet", :number 123}
   {:family "Sound Effects", :instrument "Telephone Ring", :number 124}
   {:family "Sound Effects", :instrument "Helicopter", :number 125}
   {:family "Sound Effects", :instrument "Applause", :number 126}
   {:family "Sound Effects", :instrument "Gunshot", :number 127}])

(defn find-instrument [name]
  (let [name (str/lower-case name)]
    (some #(when (-> % :instrument str/lower-case (str/includes? name))
             %) instruments)))

(defn load-instrument
  ([name] (load-instrument name false))
  ([name play-sample?]
   (if-let [{:as instr n :number} (find-instrument name)]
     (do
       (println "Loading instrument" instr)
       (load-patch synth n)
       (when play-sample? (midi-test synth)))
     (println "Couldn't find instrument named " name))))

(defn play-note
  ([note] (play-note note 250))
  ([note dur]
   (. first-channel noteOn note 127)
   (Thread/sleep dur)
   (. first-channel noteOff note)))

(defn play-notes
  ([notes] (play-notes notes 150))
  ([notes dur]
   (doseq [note notes]
     (play-note note dur))))


(def c-octave
  "https://www.inspiredacoustics.com/en/MIDI_note_numbers_and_center_frequencies"
  [48 50 52 53 55 57 59 60])

(comment
 (load-instrument "Xylophone" true)
 (load-instrument "electric Guitar" true)
 (load-instrument "voice" true)

 (play-notes c-octave 200)
 )



