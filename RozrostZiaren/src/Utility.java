import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class Utility {
    DataManager dm;
    int ID = 0;
    Color inkluzjaKolor =new Color(0,0,0);
    Nucleon inkluzjaNukleon = new Nucleon(false, (-1), inkluzjaKolor);
    Color dualPhaseKolor =new Color(255,0,0);
    Nucleon dualPhaseNukleon = new Nucleon(false, (-2), dualPhaseKolor);



    public Utility(DataManager dm) {
        this.dm = dm;
    }

    int mostFrequent(ArrayList<Integer> arr, int n) {

        // Sort the array
        Collections.sort(arr);

        // find the max frequency using linear
        // traversal
        int max_count = 1, res = arr.get(0);
        int curr_count = 1;

        for (int i = 1; i < n; i++) {
            if (arr.get(i) == arr.get(i - 1))
                curr_count++;
            else {
                if (curr_count > max_count) {
                    max_count = curr_count;
                    res = arr.get(i - 1);
                }
                curr_count = 1;
            }
        }

        // If last element is most frequent
        if (curr_count > max_count) {
            max_count = curr_count;
            res = arr.get(n - 1);
        }

        return res;
    }

    void nucelonIdentify() {
        for (int wi = 0; wi < dm.width; wi++) {
            for (int hi = 0; hi < dm.height; hi++) {

                boolean exists = false;
                Color color = new Color(dm.img.getRGB(wi, hi));

                if (dm.nucleons != null) {
                    for (Nucleon n : dm.nucleons) {
                        if (n.kolor.equals(color)) {
                            exists = true;
                        }
                    }
                }

                if (!exists) {
                    Nucleon nuclid = new Nucleon(true, ID, color);
                    dm.nucleons.add(nuclid);
                    ID++;
                }
            }
        }
    }

    boolean nucelonIdentifyNEW(int x, int y) {

        boolean exists = false;
        Color color = new Color(dm.img.getRGB(x, y));

        if (dm.nucleons != null) {
            for (Nucleon n : dm.nucleons) {
                if (n.kolor.equals(color)) {
                    exists = true;
                }
            }
        }

        if (!exists) {
            Nucleon nuclid = new Nucleon(true, ID, color);
            dm.nucleons.add(nuclid);
            ID++;
        }
        return exists;
    }

    Nucleon[] getNeighbors(int Width, int Height, int Radius) {
        if (dm.choiceNeighbor == 0 && dm.choiceBoundary == 0) {
            Nucleon[] neighbors = new Nucleon[4];

            try {
                neighbors[0] = dm.nucleonLast[Width - 1][Height];
                neighbors[1] = dm.nucleonLast[Width][Height - 1];
                neighbors[2] = dm.nucleonLast[Width][Height + 1];
                neighbors[3] = dm.nucleonLast[Width + 1][Height];
            } catch (Exception e) {
                if (Width == 0 && Height == 0) {
                    neighbors[0] = dm.nucleonLast[dm.width - 1][0];
                    neighbors[1] = dm.nucleonLast[0][dm.height - 1];
                    neighbors[2] = dm.nucleonLast[Width][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                } else if (Width == dm.width - 1 && Height == 0) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][dm.height - 1];
                    neighbors[2] = dm.nucleonLast[0][Height];
                    neighbors[3] = dm.nucleonLast[Width][Height + 1];
                } else if (Width == 0 && Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[dm.width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = dm.nucleonLast[Width + 1][Height];
                    neighbors[3] = dm.nucleonLast[Width][0];
                } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = dm.nucleonLast[0][Height];
                    neighbors[3] = dm.nucleonLast[Width][0];
                } else if (Height == 0) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][dm.height - 1];
                    neighbors[2] = dm.nucleonLast[Width + 1][Height];
                    neighbors[3] = dm.nucleonLast[Width][Height + 1];
                } else if (Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = dm.nucleonLast[Width + 1][Height];
                    neighbors[3] = dm.nucleonLast[Width][0];

                } else if (Width == 0) {
                    neighbors[0] = dm.nucleonLast[dm.width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = dm.nucleonLast[Width + 1][Height];
                    neighbors[3] = dm.nucleonLast[Width][Height + 1];
                } else if (Width == dm.width - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = dm.nucleonLast[0][Height];
                    neighbors[3] = dm.nucleonLast[Width][Height + 1];
                }
            } finally {
                return neighbors;
            }
        } //Neumann | Periodic
        else if (dm.choiceNeighbor == 0 && dm.choiceBoundary == 1) {
            Nucleon[] neighbors = new Nucleon[4];
            int licznik = 0;

            try {
                neighbors[0] = dm.nucleonLast[Width - 1][Height];
                neighbors[1] = dm.nucleonLast[Width][Height - 1];
                neighbors[2] = dm.nucleonLast[Width][Height + 1];
                neighbors[3] = dm.nucleonLast[Width + 1][Height];
            } catch (Exception e) {
                if (Width == 0 && Height == 0) {
                    neighbors[0] = new Nucleon(false);
                    neighbors[1] = new Nucleon(false);
                    neighbors[2] = dm.nucleonLast[Width][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                } else if (Width == dm.width - 1 && Height == 0) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = new Nucleon(false);
                    neighbors[2] = dm.nucleonLast[Width][Height + 1];
                    neighbors[3] = new Nucleon(false);
                } else if (Width == 0 && Height == dm.height - 1) {
                    neighbors[0] = new Nucleon(false);
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = new Nucleon(false);
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = new Nucleon(false);
                    neighbors[3] = new Nucleon(false);
                } else if (Height == 0) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = new Nucleon(false);
                    neighbors[2] = dm.nucleonLast[Width][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                } else if (Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = new Nucleon(false);
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                } else if (Width == 0) {
                    neighbors[0] = new Nucleon(false);
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = dm.nucleonLast[Width][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                } else if (Width == dm.width - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width][Height - 1];
                    neighbors[2] = dm.nucleonLast[Width][Height + 1];
                    neighbors[3] = new Nucleon(false);
                }
            } finally {
                return neighbors;
            }
        } //Neuman | Absorb
        else if (dm.choiceNeighbor == 1 && dm.choiceBoundary == 0) {
            Nucleon[] neighbors = new Nucleon[8];
            int licznik = 0;

            try {
                for (int wi = Width - 1; wi <= Width + 1; wi++) {
                    for (int hi = Height - 1; hi <= Height + 1; hi++) {

                        if (wi == Width && hi == Height) {
                        } else {
                            neighbors[licznik] = dm.nucleonLast[wi][hi];
                            licznik++;
                        }
                    }
                }
            } catch (Exception e) {
                if (Width == 0 && Height == 0) {
                    neighbors[0] = dm.nucleonLast[dm.width - 1][dm.height - 1];
                    neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                    neighbors[2] = dm.nucleonLast[dm.width - 1][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width][dm.height - 1];
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = dm.nucleonLast[Width + 1][dm.height - 1];
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = dm.nucleonLast[Width + 1][Height + 1];
                } else if (Width == dm.width - 1 && Height == 0) {
                    neighbors[0] = dm.nucleonLast[Width - 1][dm.height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width][dm.height - 1];
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = dm.nucleonLast[0][dm.height - 1];
                    neighbors[6] = dm.nucleonLast[0][Height];
                    neighbors[7] = dm.nucleonLast[0][Height + 1];
                } else if (Width == 0 && Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[dm.width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                    neighbors[2] = dm.nucleonLast[dm.width - 1][0];
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width][0];
                    neighbors[5] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = dm.nucleonLast[Width + 1][0];
                } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][0];
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width][0];
                    neighbors[5] = dm.nucleonLast[0][Height - 1];
                    neighbors[6] = dm.nucleonLast[0][Height];
                    neighbors[7] = dm.nucleonLast[0][0];
                } else if (Height == 0) {
                    neighbors[0] = dm.nucleonLast[Width - 1][dm.height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width][dm.height - 1];
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = dm.nucleonLast[Width + 1][dm.height - 1];
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = dm.nucleonLast[Width + 1][Height + 1];
                } else if (Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][0];
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width][0];
                    neighbors[5] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = dm.nucleonLast[Width + 1][0];
                } else if (Width == 0) {
                    neighbors[0] = dm.nucleonLast[dm.width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                    neighbors[2] = dm.nucleonLast[dm.width - 1][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = dm.nucleonLast[Width + 1][Height + 1];
                } else if (Width == dm.width - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = dm.nucleonLast[0][Height - 1];
                    neighbors[6] = dm.nucleonLast[0][Height];
                    neighbors[7] = dm.nucleonLast[0][Height + 1];
                }
            } finally {
                return neighbors;
            }
        } // Moore | Periodic
        else if (dm.choiceNeighbor == 1 && dm.choiceBoundary == 1) {
            Nucleon[] neighbors = new Nucleon[8];
            int licznik = 0;

            try {
                for (int wi = Width - 1; wi <= Width + 1; wi++) {
                    for (int hi = Height - 1; hi <= Height + 1; hi++) {

                        if (wi == Width && hi == Height) {
                        } else {
                            neighbors[licznik] = dm.nucleonLast[wi][hi];
                            licznik++;
                        }
                    }
                }
            } catch (Exception e) {
                if (Width == 0 && Height == 0) {
                    neighbors[0] = new Nucleon(false);
                    neighbors[1] = new Nucleon(false);
                    neighbors[2] = new Nucleon(false);
                    neighbors[3] = new Nucleon(false);
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = new Nucleon(false);
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = dm.nucleonLast[Width + 1][Height + 1];
                } else if (Width == dm.width - 1 && Height == 0) {
                    neighbors[0] = new Nucleon(false);
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[3] = new Nucleon(false);
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = new Nucleon(false);
                    neighbors[6] = new Nucleon(false);
                    neighbors[7] = new Nucleon(false);
                } else if (Width == 0 && Height == dm.height - 1) {
                    neighbors[0] = new Nucleon(false);
                    neighbors[1] = new Nucleon(false);
                    neighbors[2] = new Nucleon(false);
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = new Nucleon(false);
                    neighbors[5] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = new Nucleon(false);
                } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = new Nucleon(false);
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = new Nucleon(false);
                    neighbors[5] = new Nucleon(false);
                    neighbors[6] = new Nucleon(false);
                    neighbors[7] = new Nucleon(false);
                } else if (Height == 0) {
                    neighbors[0] = new Nucleon(false);
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[3] = new Nucleon(false);
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = new Nucleon(false);
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = dm.nucleonLast[Width + 1][Height + 1];
                } else if (Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = new Nucleon(false);
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = new Nucleon(false);
                    neighbors[5] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = new Nucleon(false);
                } else if (Width == 0) {
                    neighbors[0] = new Nucleon(false);
                    neighbors[1] = new Nucleon(false);
                    neighbors[2] = new Nucleon(false);
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[6] = dm.nucleonLast[Width + 1][Height];
                    neighbors[7] = dm.nucleonLast[Width + 1][Height + 1];
                } else if (Width == dm.width - 1) {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];
                    neighbors[5] = new Nucleon(false);
                    neighbors[6] = new Nucleon(false);
                    neighbors[7] = new Nucleon(false);
                }
            } finally {
                return neighbors;
            }
        } // Moore | Absorb
        else if (dm.choiceNeighbor == 2 && dm.choiceBoundary == 0) {
            Nucleon[] neighbors = new Nucleon[6];

            Random r = new Random();
            int losuj = r.nextInt(2);

            if (losuj == 0) {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width][Height + 1];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    neighbors[5] = dm.nucleonLast[Width + 1][Height + 1];

                } catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][dm.height - 1];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][dm.height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[0][Height];
                        neighbors[5] = dm.nucleonLast[0][Height + 1];
                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][0];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = dm.nucleonLast[Width + 1][0];
                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][0];
                        neighbors[4] = dm.nucleonLast[0][Height];
                        neighbors[5] = dm.nucleonLast[0][0];
                    } else if (Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][dm.height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][0];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = dm.nucleonLast[Width + 1][0];
                    } else if (Width == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[0][Height];
                        neighbors[5] = dm.nucleonLast[0][Height + 1];
                    }
                } finally {
                    return neighbors;
                }
            } else {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[2] = dm.nucleonLast[Width][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width][Height + 1];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[5] = dm.nucleonLast[Width + 1][Height];
                } catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][dm.height - 1];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[0][dm.height - 1];
                        neighbors[5] = dm.nucleonLast[0][Height];
                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][0];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][0];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][0];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][0];
                        neighbors[4] = dm.nucleonLast[0][Height - 1];
                        neighbors[5] = dm.nucleonLast[0][Height];
                    } else if (Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][dm.height - 1];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][0];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][0];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[0][Height - 1];
                        neighbors[5] = dm.nucleonLast[0][Height];
                    }
                } finally {
                    return neighbors;
                }

            }
        } // Hex | Periodic
        else if (dm.choiceNeighbor == 2 && dm.choiceBoundary == 1) {
            Nucleon[] neighbors = new Nucleon[6];
            int licznik = 0;

            Random r = new Random();
            int losuj = r.nextInt(2);

            if (losuj == 0) {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width][Height + 1];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    neighbors[5] = dm.nucleonLast[Width + 1][Height + 1];

                } catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = new Nucleon(false);
                        neighbors[5] = new Nucleon(false);
                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = new Nucleon(false);
                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                        neighbors[5] = new Nucleon(false);
                    } else if (Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = new Nucleon(false);
                    } else if (Width == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = new Nucleon(false);
                        neighbors[5] = new Nucleon(false);
                    }
                } finally {
                    return neighbors;
                }
            } else {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[2] = dm.nucleonLast[Width][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width][Height + 1];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[5] = dm.nucleonLast[Width + 1][Height];
                } catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = new Nucleon(false);
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = new Nucleon(false);
                        neighbors[5] = new Nucleon(false);
                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                        neighbors[5] = new Nucleon(false);
                    } else if (Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = new Nucleon(false);
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[5] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width][Height + 1];
                        neighbors[4] = new Nucleon(false);
                        neighbors[5] = new Nucleon(false);
                    }
                } finally {
                    return neighbors;
                }

            }
        } // Hex | Absorb
        else if (dm.choiceNeighbor == 3 && dm.choiceBoundary == 0) {
            Nucleon[] neighbors = new Nucleon[5];

            Random r = new Random();
            int losuj = r.nextInt(4);

            if (losuj == 0) {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];

                } catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][dm.height - 1];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[2] = dm.nucleonLast[dm.width - 1][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][dm.height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[2] = dm.nucleonLast[dm.width - 1][0];
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width][0];

                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width - 1][0];
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width][0];

                    } else if (Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][dm.height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width - 1][0];
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width][0];

                    } else if (Width == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[2] = dm.nucleonLast[dm.width - 1][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    }
                } finally {
                    return neighbors;
                }
            }
            else if (losuj == 1) {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height];
                } catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][dm.height - 1];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][dm.height - 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][dm.height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[3] = dm.nucleonLast[0][dm.height - 1];
                        neighbors[4] = dm.nucleonLast[0][Height];
                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[0][Height - 1];
                        neighbors[4] = dm.nucleonLast[0][Height];
                    } else if (Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][dm.height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][dm.height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][dm.height - 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[0][Height - 1];
                        neighbors[4] = dm.nucleonLast[0][Height];
                    }
                } finally {
                    return neighbors;
                }
            }
            else if (losuj == 2){
                try {
                    neighbors[0] = dm.nucleonLast[Width][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width][Height + 1];
                    neighbors[2] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                }
                catch (Exception e) {
                if (Width == 0 && Height == 0) {
                    neighbors[0] = dm.nucleonLast[Width][dm.height - 1];
                    neighbors[1] = dm.nucleonLast[Width][Height + 1];
                    neighbors[2] = dm.nucleonLast[Width + 1][dm.height - 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                } else if (Width == dm.width - 1 && Height == 0) {
                    neighbors[0] = dm.nucleonLast[Width][dm.height - 1];
                    neighbors[1] = dm.nucleonLast[Width][Height + 1];
                    neighbors[2] = dm.nucleonLast[0][dm.height - 1];
                    neighbors[3] = dm.nucleonLast[0][Height];
                    neighbors[4] = dm.nucleonLast[0][Height + 1];
                } else if (Width == 0 && Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width][0];
                    neighbors[2] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                    neighbors[4] = dm.nucleonLast[Width + 1][0];
                } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width][0];
                    neighbors[2] = dm.nucleonLast[0][Height - 1];
                    neighbors[3] = dm.nucleonLast[0][Height];
                    neighbors[4] = dm.nucleonLast[0][0];
                } else if (Height == 0) {
                    neighbors[0] = dm.nucleonLast[Width][dm.height - 1];
                    neighbors[1] = dm.nucleonLast[Width][Height + 1];
                    neighbors[2] = dm.nucleonLast[Width + 1][dm.height - 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                } else if (Height == dm.height - 1) {
                    neighbors[0] = dm.nucleonLast[Width][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width][0];
                    neighbors[2] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                    neighbors[4] = dm.nucleonLast[Width + 1][0];
                } else if (Width == 0) {
                    neighbors[0] = dm.nucleonLast[Width][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width][Height + 1];
                    neighbors[2] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                } else if (Width == dm.width - 1) {
                    neighbors[0] = dm.nucleonLast[Width][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width][Height + 1];
                    neighbors[2] = dm.nucleonLast[0][Height - 1];
                    neighbors[3] = dm.nucleonLast[0][Height];
                    neighbors[4] = dm.nucleonLast[0][Height + 1];
                }
            } finally {
                return neighbors;
            }

            }
            else {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[2] = dm.nucleonLast[Width][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                }
                catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = dm.nucleonLast[0][Height];
                        neighbors[4] = dm.nucleonLast[0][Height + 1];
                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][0];
                        neighbors[2] = dm.nucleonLast[Width][0];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][0];
                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][0];
                        neighbors[2] = dm.nucleonLast[Width][0];
                        neighbors[3] = dm.nucleonLast[0][Height];
                        neighbors[4] = dm.nucleonLast[0][0];
                    } else if (Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][0];
                        neighbors[2] = dm.nucleonLast[Width][0];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][0];
                    } else if (Width == 0) {
                        neighbors[0] = dm.nucleonLast[dm.width - 1][Height];
                        neighbors[1] = dm.nucleonLast[dm.width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = dm.nucleonLast[0][Height];
                        neighbors[4] = dm.nucleonLast[0][Height + 1];
                    }
                }
                finally {
                    return neighbors;
                }

                }
            }// Pentagonal | Periodic
        else if (dm.choiceNeighbor == 3 && dm.choiceBoundary == 1) {
            Nucleon[] neighbors = new Nucleon[5];

            Random r = new Random();
            int losuj = r.nextInt(4);

            if (losuj == 0) {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width][Height + 1];

                } catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = new Nucleon(false);

                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = new Nucleon(false);

                    } else if (Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] =  dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = new Nucleon(false);

                    } else if (Width == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width][Height + 1];

                    }
                } finally {
                    return neighbors;
                }
            }
            else if (losuj == 1) {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height];
                    neighbors[2] = dm.nucleonLast[Width][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height];
                } catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                    } else if (Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height];
                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height];
                        neighbors[2] = dm.nucleonLast[Width][Height - 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                    }
                } finally {
                    return neighbors;
                }
            }
            else if (losuj == 2){
                try {
                    neighbors[0] = dm.nucleonLast[Width][Height - 1];
                    neighbors[1] = dm.nucleonLast[Width][Height + 1];
                    neighbors[2] = dm.nucleonLast[Width + 1][Height - 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                }
                catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = dm.nucleonLast[Width][Height + 1];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = dm.nucleonLast[Width][Height + 1];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width][Height - 1];
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = new Nucleon(false);
                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width][Height - 1];
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                    } else if (Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = dm.nucleonLast[Width][Height + 1];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width][Height - 1];
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = new Nucleon(false);
                    } else if (Width == 0) {
                        neighbors[0] = dm.nucleonLast[Width][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width + 1][Height - 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width][Height - 1];
                        neighbors[1] = dm.nucleonLast[Width][Height + 1];
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                    }
                } finally {
                    return neighbors;
                }

            }
            else {
                try {
                    neighbors[0] = dm.nucleonLast[Width - 1][Height];
                    neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                    neighbors[2] = dm.nucleonLast[Width][Height + 1];
                    neighbors[3] = dm.nucleonLast[Width + 1][Height];
                    neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                }
                catch (Exception e) {
                    if (Width == 0 && Height == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1 && Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                    } else if (Width == 0 && Height == dm.height - 1) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = new Nucleon(false);
                    } else if (Width == dm.width - 1 && Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                    } else if (Height == 0) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Height == dm.height - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = new Nucleon(false);
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = new Nucleon(false);
                    } else if (Width == 0) {
                        neighbors[0] = new Nucleon(false);
                        neighbors[1] = new Nucleon(false);
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = dm.nucleonLast[Width + 1][Height];
                        neighbors[4] = dm.nucleonLast[Width + 1][Height + 1];
                    } else if (Width == dm.width - 1) {
                        neighbors[0] = dm.nucleonLast[Width - 1][Height];
                        neighbors[1] = dm.nucleonLast[Width - 1][Height + 1];
                        neighbors[2] = dm.nucleonLast[Width][Height + 1];
                        neighbors[3] = new Nucleon(false);
                        neighbors[4] = new Nucleon(false);
                    }
                }
                finally {
                    return neighbors;
                }

            }
        }// Pentagonal | Absorb
        else if (dm.choiceNeighbor == 4 && dm.choiceBoundary == 0){

            double check=((Math.pow((Radius+Radius+1),2))-1);
            Nucleon[] neighbors = new Nucleon[(int)check];
            int licznik = 0;

            for (int wi = Width - Radius; wi <= Width + Radius; wi++) {
                for (int hi = Height - Radius; hi <= Height + Radius; hi++) {
                    int tempx=Math.floorMod(wi,dm.width);
                    int tempy=Math.floorMod(hi,dm.height);

                    if (wi == Width && hi == Height) {}
                    else {
                        neighbors[licznik] = dm.nucleonLast[tempx][tempy];
                        licznik++;
                    }
                }
            }
            return neighbors;
        }//Radius | Periodic
        else {

            double check=((Math.pow((Radius+Radius+1),2))-1);
            Nucleon[] neighbors = new Nucleon[(int)check];
            int licznik = 0;

            for (int wi = Width - Radius; wi <= Width + Radius; wi++) {
                for (int hi = Height - Radius; hi <= Height + Radius; hi++) {
                    int tempx=Math.floorMod(wi,dm.width);
                    int tempy=Math.floorMod(hi,dm.height);

                    if (wi == Width && hi == Height) {}
                    else if (wi<=0 || hi<=0){
                        neighbors[licznik] = new Nucleon(false);
                        licznik++;
                    }
                    else if (wi>=dm.width || hi>=dm.height){
                        neighbors[licznik] = new Nucleon(false);
                        licznik++;
                    }
                    else {
                        neighbors[licznik] = dm.nucleonLast[tempx][tempy];
                        licznik++;
                    }
                }
            }
            return neighbors;
        }//Radius | Absorb
    }

    void drawPixel (int x, int y){
        if(dm.img.getRGB(x,y)==inkluzjaKolor.getRGB()){return;}
        if(dm.img.getRGB(x,y)==dualPhaseKolor.getRGB()){return;}

        Random r = new Random();
        int red=r.nextInt(254)+1;
        int green=r.nextInt(254)+1;
        int blue=r.nextInt(254)+1;

        Color kolor=new Color(red,green,blue);
        dm.img.setRGB(x,y,kolor.getRGB());

        boolean check=nucelonIdentifyNEW(x,y);
        if (check==true) {
            drawPixel(x, y);
        }

        Nucleon test= dm.nucleons.get(dm.nucleons.size()-1);

        dm.nucleonLast[x][y].isActive=test.isActive;
        dm.nucleonLast[x][y].kolor=test.kolor;
        dm.nucleonLast[x][y].ID=test.ID;

        dm.nucleonCurrent[x][y].isActive=test.isActive;
        dm.nucleonCurrent[x][y].kolor=test.kolor;
        dm.nucleonCurrent[x][y].ID=test.ID;

        //System.out.println(dm.nucleons.get(dm.nucleons.size()-1).ID);
    }

    void animateNucleonGrow(){
            for (int hi = 0; hi < dm.height ; hi++) {
                for (int wi = 0; wi < dm.width; wi++) {


                    Nucleon[] localNeighbor = getNeighbors(wi, hi,dm.radiusNeighbor);
                    Nucleon localMiddle = dm.nucleonLast[wi][hi];

                    ArrayList<Integer> localCount = new ArrayList<Integer>();


                        for (int j=0;j< localNeighbor.length;j++) { //liczba wystapien poszczegolnych Sasiadow
                            if (localNeighbor[j].ID>0)
                            {
                                localCount.add(localNeighbor[j].ID);
                            }
                        }

                        if(localCount.isEmpty()==false) {

                            int foundMostFreq = mostFrequent(localCount, localCount.toArray().length);

                            if (localMiddle.ID == 0) {
                                dm.nucleonCurrent[wi][hi].kolor = dm.nucleons.get(foundMostFreq).kolor;
                                dm.nucleonCurrent[wi][hi].ID = dm.nucleons.get(foundMostFreq).ID;
                            }
                        }
                        }
                    }

            for (int x = 0; x < dm.width; x++) {
                for (int z = 0; z < dm.height; z++) {
                    dm.nucleonLast[x][z].kolor=dm.nucleonCurrent[x][z].kolor;
                    dm.nucleonLast[x][z].ID=dm.nucleonCurrent[x][z].ID;

                    dm.img.setRGB(x, z, dm.nucleonCurrent[x][z].kolor.getRGB());

                }
            }
    }

    void placeNucleonRegular(int row, int column) {

        int row1=Math.abs(row);
        int column1=Math.abs(column);
            int tempWidth = (dm.width/row1);
            int tempHeight = (dm.height/column1);

            for(int i=0; i<row1;i++){
                for(int j=0; j<column1;j++){
                    drawPixel(tempWidth*i,tempHeight*j);
                }
            }
    }

    void placeNucleonRandom(int number){
        Random r = new Random();
        for(int i=0; i<Math.abs(number);i++){
            drawPixel(r.nextInt(dm.width),r.nextInt(dm.height));
        }
    }


    //==============IMPORT/EXPORT==============

    void kolorforID(int inputID){

        if (dm.colors.containsKey(inputID)==true){}
        else {
            Random random = new Random();

            // Jeśli nie, wygenerowanie losowego koloru
            int r = random.nextInt(254);
            int g = random.nextInt(254);
            int b = random.nextInt(254);
            Color kolor = new Color(r, g, b);

            dm.colors.put(inputID, kolor);
        }

    }

    void IDforColor(Color inputColor){

        if (dm.identificators.containsKey(inputColor)==true){}
        else {
            dm.identificators.put(inputColor, ID);
            ID++;
        }

    }

    void nucelonIdentifyTXT() {
        for (int wi = 0; wi < dm.width; wi++) {
            for (int hi = 0; hi < dm.height; hi++) {

                boolean exists = false;
                Color color = new Color(dm.img.getRGB(wi, hi));
                if (color.getRGB() != inkluzjaKolor.getRGB() && color.getRGB()!=dualPhaseKolor.getRGB()) {

                    if (dm.nucleons != null) {
                        for (Nucleon n : dm.nucleons) {
                            if (n.kolor.equals(color)) {
                                exists = true;
                            }
                        }
                    }

                    if (!exists) {
                        Nucleon nuclid = new Nucleon(true, ID, dm.colors.get(ID));
                        dm.nucleons.add(nuclid);
                        ID++;
                    }
                }
            }
        }
    }

    void nucelonIdentifyBMP() {
        for (int wi = 0; wi < dm.width; wi++) {
            for (int hi = 0; hi < dm.height; hi++) {

                boolean exists = false;
                Color color = new Color(dm.img.getRGB(wi, hi));
                if (color.getRGB() != inkluzjaKolor.getRGB() && color.getRGB()!=dualPhaseKolor.getRGB()) {
                    if (dm.nucleons != null) {
                        for (Nucleon n : dm.nucleons) {
                            if (n.kolor.equals(color)) {
                                exists = true;
                            }
                        }
                    }

                    if (!exists) {
                        Nucleon nuclid = new Nucleon(true, dm.identificators.get(color), color);
                        dm.nucleons.add(nuclid);
                    }
                }
            }
        }
    }

    void importFromTXT(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("import.txt"));

            String firstLine = reader.readLine();
            Scanner scannerFirstLine = new Scanner(firstLine);

            //System.out.println("Pierwsza linia: " + firstLine);
            int width = scannerFirstLine.nextInt();
            int height = scannerFirstLine.nextInt();

            dm.width=width;
            dm.height=height;

            dm.img=new BufferedImage(1,1,BufferedImage.TYPE_BYTE_GRAY);
            dm.width=1;
            dm.height=1;
            dm.nucleonLast=new Nucleon [1][1];
            dm.nucleonCurrent=new Nucleon [1][1];
            Initial();

            dm.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            dm.width = width;
            dm.height = height;
            dm.nucleonLast=new Nucleon [dm.width][dm.height];
            dm.nucleonCurrent=new Nucleon [dm.width][dm.height];
            Initial();


            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                Scanner scanner = new Scanner(line);
                while (scanner.hasNextInt()) {
                    int posX = scanner.nextInt();
                    int posY = scanner.nextInt();
                    int IDfromTXT = scanner.nextInt();

                    if (IDfromTXT>0) {
                        kolorforID(IDfromTXT);
                        dm.img.setRGB(posX, posY, dm.colors.get(IDfromTXT).getRGB());

                        dm.nucleonCurrent[posX][posY].ID = IDfromTXT;
                        dm.nucleonLast[posX][posY].ID = IDfromTXT;

                        dm.nucleonCurrent[posX][posY].kolor = dm.colors.get(IDfromTXT);
                        dm.nucleonLast[posX][posY].kolor = dm.colors.get(IDfromTXT);

                        //Nucleon nuclid = new Nucleon(true, IDfromTXT, dm.colors.get(IDfromTXT));
                        //dm.nucleons.add(nuclid);

                        //if(ID<IDfromTXT){ID=IDfromTXT;}

                    }
                    if(IDfromTXT==(-1))
                    {
                        dm.img.setRGB(posX, posY, inkluzjaKolor.getRGB());

                        dm.nucleonCurrent[posX][posY].ID = inkluzjaNukleon.ID;
                        dm.nucleonLast[posX][posY].ID = inkluzjaNukleon.ID;

                        dm.nucleonCurrent[posX][posY].isActive = inkluzjaNukleon.isActive;
                        dm.nucleonLast[posX][posY].isActive = inkluzjaNukleon.isActive;

                        dm.nucleonCurrent[posX][posY].kolor = inkluzjaKolor;
                        dm.nucleonLast[posX][posY].kolor = inkluzjaKolor;
                    }

                    if(IDfromTXT==(-2))
                    {
                        dm.img.setRGB(posX, posY, dualPhaseKolor.getRGB());

                        dm.nucleonCurrent[posX][posY].ID = dualPhaseNukleon.ID;
                        dm.nucleonLast[posX][posY].ID = dualPhaseNukleon.ID;

                        dm.nucleonCurrent[posX][posY].isActive = dualPhaseNukleon.isActive;
                        dm.nucleonLast[posX][posY].isActive = dualPhaseNukleon.isActive;

                        dm.nucleonCurrent[posX][posY].kolor = dualPhaseKolor;
                        dm.nucleonLast[posX][posY].kolor = dualPhaseKolor;
                    }
                }
            }
            nucelonIdentifyTXT();
            reader.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    void importFromBMP() {

        try {
            File file = new File("import.bmp");

            // Wczytujemy obraz z pliku do obiektu typu BufferedImage
            BufferedImage image = ImageIO.read(file);

            int width = image.getWidth();
            int height = image.getHeight();

            dm.width = width;
            dm.height = height;

            dm.img = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
            dm.width = 1;
            dm.height = 1;
            dm.nucleonLast = new Nucleon[1][1];
            dm.nucleonCurrent = new Nucleon[1][1];
            Initial();

            dm.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);;
            dm.width = width;
            dm.height = height;
            dm.nucleonLast = new Nucleon[dm.width][dm.height];
            dm.nucleonCurrent = new Nucleon[dm.width][dm.height];
            Initial();

            for (int wi = 0; wi < dm.width; wi++) {
                for (int hi = 0; hi < dm.height; hi++) {
                    int Red=image.getRaster().getSample(wi,hi,0);
                    int Green=image.getRaster().getSample(wi,hi,1);
                    int Blue=image.getRaster().getSample(wi,hi,2);

                    Color color = new Color(Red,Green,Blue);
                    dm.img.setRGB(wi, hi, color.getRGB());
                    if(color.getRGB()!=dm.nucleons.get(0).kolor.getRGB() && color.getRGB()!=inkluzjaKolor.getRGB() && color.getRGB()!=dualPhaseKolor.getRGB() )
                    {
                        IDforColor(color);
                        dm.nucleonCurrent[wi][hi].ID = dm.identificators.get(color);
                        dm.nucleonLast[wi][hi].ID = dm.identificators.get(color);

                        dm.nucleonCurrent[wi][hi].kolor = color;
                        dm.nucleonLast[wi][hi].kolor = color;
                    }

                    else if(color.getRGB()==inkluzjaKolor.getRGB())
                    {
                        dm.nucleonCurrent[wi][hi].ID = inkluzjaNukleon.ID;
                        dm.nucleonLast[wi][hi].ID = inkluzjaNukleon.ID;

                        dm.nucleonCurrent[wi][hi].isActive = inkluzjaNukleon.isActive;
                        dm.nucleonLast[wi][hi].isActive = inkluzjaNukleon.isActive;

                        dm.nucleonCurrent[wi][hi].kolor = color;
                        dm.nucleonLast[wi][hi].kolor = color;
                    }

                    else if(color.getRGB()==dualPhaseKolor.getRGB())
                    {
                        dm.nucleonCurrent[wi][hi].ID = dualPhaseNukleon.ID;
                        dm.nucleonLast[wi][hi].ID = dualPhaseNukleon.ID;

                        dm.nucleonCurrent[wi][hi].isActive = dualPhaseNukleon.isActive;
                        dm.nucleonLast[wi][hi].isActive = dualPhaseNukleon.isActive;

                        dm.nucleonCurrent[wi][hi].kolor = color;
                        dm.nucleonLast[wi][hi].kolor = color;
                    }

                    else
                    {
                        dm.nucleonCurrent[wi][hi].ID = 0;
                        dm.nucleonLast[wi][hi].ID = 0;

                        dm.nucleonCurrent[wi][hi].kolor = color;
                        dm.nucleonLast[wi][hi].kolor = color;
                    }
                }
            }
            nucelonIdentifyBMP();
        }
        catch (Exception e) {
            // W przypadku wystąpienia błędu wyświetlamy informacje o nim
            e.printStackTrace();
        }
    }

    void exportToTXT(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("export.txt"));

            String dimensions = (dm.width + " " + dm.height );
            String coordinatesData;
            writer.write(dimensions);
            writer.newLine();

            for (int wi = 0; wi < dm.width; wi++) {
                for (int hi = 0; hi < dm.height; hi++) {
                    coordinatesData=(wi+ " " + hi + " " +dm.nucleonCurrent[wi][hi].ID);
                    writer.write(coordinatesData);
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    //==============INCLUSIONS==============

    void drawInclusionsCircular(int inputNumber,int inputSize) {
        Random r = new Random();
        inputSize=inputSize/2; //because inputSize is  diameter and the radius is used in the calculation
        int x,y;
        for(int z=0; z<Math.abs(inputNumber);z++) {
            x = r.nextInt(dm.width);
            y = r.nextInt(dm.height);

            dm.img.setRGB(x,y,inkluzjaKolor.getRGB());

            dm.nucleonLast[x][y].isActive=inkluzjaNukleon.isActive;
            dm.nucleonLast[x][y].kolor=inkluzjaNukleon.kolor;
            dm.nucleonLast[x][y].ID=inkluzjaNukleon.ID;

            dm.nucleonCurrent[x][y].isActive=inkluzjaNukleon.isActive;
            dm.nucleonCurrent[x][y].kolor=inkluzjaNukleon.kolor;
            dm.nucleonCurrent[x][y].ID=inkluzjaNukleon.ID;

            int radiusSquared = inputSize * inputSize;
            for (int i = 0; i < dm.width; i++) {
                for (int j = 0; j < dm.height; j++) {
                    int distanceSquared = (i - x) * (i - x) + (j - y) * (j - y);
                    if (distanceSquared <= radiusSquared) {
                        dm.img.setRGB(i,j,inkluzjaKolor.getRGB());

                        dm.nucleonLast[i][j].isActive=inkluzjaNukleon.isActive;
                        dm.nucleonLast[i][j].kolor=inkluzjaNukleon.kolor;
                        dm.nucleonLast[i][j].ID=inkluzjaNukleon.ID;

                        dm.nucleonCurrent[i][j].isActive=inkluzjaNukleon.isActive;
                        dm.nucleonCurrent[i][j].kolor=inkluzjaNukleon.kolor;
                        dm.nucleonCurrent[i][j].ID=inkluzjaNukleon.ID;

                    }
                }
            }
        }
    }

    void drawInclusionsSquared(int inputNumber,int inputSize) {

        Random r = new Random();
        int x,y;
        for(int z=0; z<Math.abs(inputNumber);z++) {
            x = r.nextInt(dm.width);
            y = r.nextInt(dm.height);

            dm.img.setRGB(x,y,inkluzjaKolor.getRGB());

            dm.nucleonLast[x][y].isActive=inkluzjaNukleon.isActive;
            dm.nucleonLast[x][y].kolor=inkluzjaNukleon.kolor;
            dm.nucleonLast[x][y].ID=inkluzjaNukleon.ID;

            dm.nucleonCurrent[x][y].isActive=inkluzjaNukleon.isActive;
            dm.nucleonCurrent[x][y].kolor=inkluzjaNukleon.kolor;
            dm.nucleonCurrent[x][y].ID=inkluzjaNukleon.ID;


            //int radiusSquared = inputSize * inputSize;
            for (int i = x; i < x+inputSize; i++) {
                for (int j = y; j < y+inputSize; j++) {
                    //int distanceSquared = (i - x) * (i - x) + (j - y) * (j - y);
                    if(i < dm.width && j < dm.height){
                    //if (distanceSquared <= radiusSquared) {
                        //[i][j] = 1;
                        dm.img.setRGB(i,j,inkluzjaKolor.getRGB());

                        dm.nucleonLast[i][j].isActive=inkluzjaNukleon.isActive;
                        dm.nucleonLast[i][j].kolor=inkluzjaNukleon.kolor;
                        dm.nucleonLast[i][j].ID=inkluzjaNukleon.ID;

                        dm.nucleonCurrent[i][j].isActive=inkluzjaNukleon.isActive;
                        dm.nucleonCurrent[i][j].kolor=inkluzjaNukleon.kolor;
                        dm.nucleonCurrent[i][j].ID=inkluzjaNukleon.ID;


                    }
                }
            }
        }
    }

    //==============DUAL_PHASE==============
    int getRandomID(){
        Random r = new Random();
        int randomID = r.nextInt(dm.nucleons.size()-1)+1;

        return randomID;
    }

    void dualPhase(int inputNumber) {

        List<Integer> list = new ArrayList<Integer>();

        for(int z=0; z<Math.abs(inputNumber);z++) {

            int randomID = getRandomID();

            do{
                randomID=getRandomID();
            }while(list.contains(randomID));

            list.add(randomID);
            //System.out.println(randomID);

            for (int x = 0; x < dm.height; x++) {
                for (int y = 0; y < dm.width; y++) {
                    Color kolor = new Color(dm.img.getRGB(x, y));

                    if (dm.nucleonCurrent[x][y].ID == randomID)
                    {
                        //if (kolor.getRGB() != dualPhaseKolor.getRGB()) {

                            dm.img.setRGB(x, y, dualPhaseKolor.getRGB());

                            dm.nucleonLast[x][y].isActive = dualPhaseNukleon.isActive;
                            dm.nucleonLast[x][y].kolor = dualPhaseNukleon.kolor;
                            dm.nucleonLast[x][y].ID = dualPhaseNukleon.ID;

                            dm.nucleonCurrent[x][y].isActive = dualPhaseNukleon.isActive;
                            dm.nucleonCurrent[x][y].kolor = dualPhaseNukleon.kolor;
                            dm.nucleonCurrent[x][y].ID = dualPhaseNukleon.ID;
                        //}
                    }
                }
            }
        }

        for (int x = 0; x < dm.height; x++) {
            for (int y = 0; y < dm.width; y++) {
                Color kolor = new Color(dm.img.getRGB(x, y));

                if (kolor.getRGB() != dualPhaseKolor.getRGB()) {
                    dm.img.setRGB(x, y, Color.WHITE.getRGB());

                    dm.nucleonLast[x][y].isActive = true;
                    dm.nucleonLast[x][y].kolor = Color.WHITE;
                    dm.nucleonLast[x][y].ID = 0;

                    dm.nucleonCurrent[x][y].isActive = true;
                    dm.nucleonCurrent[x][y].kolor = Color.WHITE;
                    dm.nucleonCurrent[x][y].ID = 0;
                }
            }
        }
    }


    void Initial() {
        for (int wi = 0; wi < dm.width; wi++) {
            for (int hi = 0; hi < dm.height; hi++) {
                dm.img.setRGB(wi, hi, Color.WHITE.getRGB());
                dm.nucleonCurrent[wi][hi]=new Nucleon(true,0,Color.WHITE);
                dm.nucleonLast[wi][hi]=new Nucleon(true,0,Color.WHITE);

            }
        }
        nucelonIdentify();
    }

}