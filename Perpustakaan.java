import java.util.Scanner;

public class Perpustakaan {
    static Scanner input = new Scanner(System.in);
    static Buku[] daftarBuku = new Buku[50];
    static Mahasiswa[] daftarMahasiswa = new Mahasiswa[50];
    static Peminjaman[] daftarPeminjaman = new Peminjaman[50];
    static int banyakBuku = 0;
    static int banyakMahasiswa = 0;
    static int banyakPeminjaman = 0;

    public static void main(String[] args) {
        char back;
        int pilih;

        do {
            System.out.println("\t\t\t*** STT-LIB ***");
            System.out.println("\t\t\t-- MENU UTAMA --");
            System.out.println("1. Input Buku");
            System.out.println("2. Daftar Buku");
            System.out.println("3. Pencarian");
            System.out.println("4. Peminjaman");
            System.out.println("5. Pengembalian");
            System.out.println("6. Keluar\n");

            System.out.print("Masukan Pilihan : ");
            pilih = input.nextInt();

            switch (pilih) {
                case 1:
                    inputBuku();
                    break;
                case 2:
                    System.out.println("\t\t\t-- DAFTAR BUKU --\n");
                    daftarBuku();
                    break;
                case 3:
                    cari();
                    break;
                case 4:
                    pinjamBuku();
                    break;
                case 5:
                    pengembalian();
                    break;
                case 6:
                    System.out.println("Anda telah keluar program !");
                    break;
                default:
                    System.out.println("Anda memasukan pilihan yang salah !");
            }

            if (pilih == 6) {
                back = 'T';
            } else {
                System.out.print("\n\nKembali ke Menu Utama ? [Y/T] : ");
                back = input.next().charAt(0);
                System.out.println("-----------------------------------------------------------------");
            }
        } while (back != 'T');
    }

    public static void inputBuku() {
        System.out.println("\t\t\t-- INPUT BUKU --");
        int jml, no = 1;
        System.out.print("Masukan jumlah buku : ");
        jml = input.nextInt();
        for (int i = banyakBuku; i < banyakBuku + jml; i++) {
            System.out.println("Buku ke " + no);
            System.out.print("Kode Buku : ");
            int kodeBuku = input.nextInt();
            input.nextLine();
            System.out.print("Judul Buku : ");
            String judulBuku = input.nextLine();
            System.out.print("Pengarang : ");
            String pengarang = input.nextLine();
            System.out.print("Tahun Terbit : ");
            int tahunTerbit = input.nextInt();

            daftarBuku[i] = new Buku(kodeBuku, judulBuku, pengarang, tahunTerbit);

            no++;
        }
        banyakBuku += jml;
        System.out.print("Buku berhasil disimpan !");
    }

    public static void daftarBuku() {
        System.out.println("Kode     Judul     Pengarang     Tahun");
        System.out.println("=============================================");
        for (int i = 0; i < banyakBuku; i++) {
            System.out.println(daftarBuku[i].kode + "     " + daftarBuku[i].judul + "     " + daftarBuku[i].pengarang + "     " + daftarBuku[i].tahunTerbit);
        }
    }

    public static void pinjamBuku() {
        System.out.println("\t\t\t-- PINJAM BUKU --");
        daftarBuku();
        int jml, no = 1;
        input.nextLine();
        System.out.print("Masukan Nama Anda : ");
        String namaMahasiswa = input.nextLine();
        System.out.print("NPM : ");
        int npmMahasiswa = input.nextInt();
        System.out.println("1. Teknik Informatika");
        System.out.println("2. Teknik Industri");
        System.out.println("3. Teknik Sipil");
        System.out.println("4. Teknik Komputer");
        System.out.print("Jurusan : ");
        int jurusanMahasiswa = input.nextInt();

        daftarMahasiswa[banyakMahasiswa] = new Mahasiswa(namaMahasiswa, npmMahasiswa, jurusanMahasiswa, 0);

        String pesan = "";
        do {
            System.out.print("Masukan Tanggal Pinjam [1-31] : ");
            int tanggalPinjam = input.nextInt();
            if (tanggalPinjam <= 0 || tanggalPinjam > 31) {
                System.out.println("Tanggal Salah !");
            }
        } while (tanggalPinjam <= 0 || tanggalPinjam > 31);

        if (tanggalPinjam < 25) {
            daftarMahasiswa[banyakMahasiswa].tanggalKembali = (tanggalPinjam) + 7;
        } else {
            daftarMahasiswa[banyakMahasiswa].tanggalKembali = ((tanggalPinjam) + 7) - 30;
        }

        System.out.print("Masukan Jumlah Buku yang dipinjam : ");
        jml = input.nextInt();
        for (int i = banyakPeminjaman; i < banyakPeminjaman + jml; i++) {
            System.out.println("Buku Ke-" + no);
            System.out.print("Masukan kode buku : ");
            int kodeBuku = input.nextInt();

            daftarPeminjaman[i] = new Peminjaman(kodeBuku, npmMahasiswa);

            boolean bukuTersedia = false;
            for (int j = 0; j < banyakBuku; j++) {
                if (daftarBuku[j].kode == kodeBuku) {
                    System.out.println("Anda meminjam " + daftarBuku[j].judul);
                    bukuTersedia = true;
                    pesan = "";
                    break;
                }
            }

            if (!bukuTersedia) {
                pesan = "Kode buku tidak tersedia\n";
            }

            System.out.print(pesan);
            no++;
        }

        daftarMahasiswa[banyakMahasiswa].jumlahPinjam = jml;
        banyakPeminjaman += jml;
        banyakMahasiswa++;
    }

    public static void pengembalian() {
        if (banyakPeminjaman == 0) {
            System.out.println("\tBelum ada yang meminjam buku...");
        } else {
            System.out.print("NPM      ");
            System.out.print("MHS      ");
            System.out.print("Jurusan      ");
            System.out.print("BanyakPinjam      ");
            System.out.print("Tgl      ");
            System.out.println("Kembali   ");
            System.out.println("==============================");

            for (int i = 0; i < banyakMahasiswa; i++) {
                System.out.print(daftarMahasiswa[i].npm + "      ");
                System.out.print(daftarMahasiswa[i].nama + "       ");

                switch (daftarMahasiswa[i].jurusan) {
                    case 1:
                        System.out.print("T. Informatika");
                        break;
                    case 2:
                        System.out.print("T. Industri");
                        break;
                    case 3:
                        System.out.print("T. Sipil");
                        break;
                    case 4:
                        System.out.print("T. Komputer");
                        break;
                    default:
                        System.out.print("Unknown");
                        break;
                }

                System.out.print("   " + daftarMahasiswa[i].jumlahPinjam + " buku       ");
                System.out.print(daftarMahasiswa[i].tanggalPinjam + "      ");
                System.out.println(daftarMahasiswa[i].tanggalKembali + "      ");
            }

            System.out.print("\nMasukan NPM\t: ");
            int npm = input.nextInt();
            for (int i = 0; i < banyakMahasiswa; i++) {
                if (npm == daftarMahasiswa[i].npm) {
                    do {
                        System.out.print("\tMasukan Tanggal Sekarang [1-31]\t: ");
                        int tanggalSekarang = input.nextInt();
                        if (tanggalSekarang <= 0 || tanggalSekarang > 31) {
                            System.out.print("\tTanggal salah !\n");
                        }
                    } while (tanggalSekarang <= 0 || tanggalSekarang > 31);

                    if (tanggalSekarang > daftarMahasiswa[i].tanggalKembali) {
                        System.out.println("\tAnda didenda Rp. 500 karena telat mengembalikan");
                    }

                    for (int j = 0; j < banyakPeminjaman; j++) {
                        if (daftarPeminjaman[j].npmMahasiswa == daftarMahasiswa[i].npm) {
                            if (daftarMahasiswa[banyakMahasiswa - 1].npm == npm) {
                                banyakMahasiswa -= 1;
                                break;
                            } else {
                                for (int x = 0; x < banyakMahasiswa; x++) {
                                    daftarMahasiswa[x] = daftarMahasiswa[x + 1];
                                    daftarPeminjaman[j].npmMahasiswa = daftarPeminjaman[j + 1].npmMahasiswa;
                                    daftarPeminjaman[j].kodeBuku = daftarPeminjaman[j + 1].kodeBuku;
                                }
                                banyakMahasiswa -= 1;
                                break;
                            }
                        }
                    }

                    System.out.print("\tSilahkan kembalikan buku yang anda pinjam..");
                }
            }
        }
    }

    public static void cari() {
        int pilih2;
        char jawab;

        do {
            System.out.println("\n\t\tCARI BUKU BERDASARKAN :");
            System.out.println("\t1. Kode Buku");
            System.out.println("\t2. Pengarang");
            System.out.print("\tMasukan pilihan anda\t: ");
            pilih2 = input.nextInt();

            switch (pilih2) {
                case 1: {
                    int kode, a = 0;
                    String pesan = "";
                    System.out.print("\tMasukan Kode Buku\t: ");
                    kode = input.nextInt();
                    System.out.println("\tHASIL PENCARIAN\t: ");

                    for (int i = 0; i < banyakBuku; i++) {
                        if (daftarBuku[i].kode == kode) {
                            System.out.println("\tKode Buku\t: " + daftarBuku[i].kode);
                            System.out.println("\tJudul Buku\t: " + daftarBuku[i].judul);
                            System.out.println("\tNama Pengarang\t: " + daftarBuku[i].pengarang);
                            pesan = "";
                            break;
                        } else {
                            pesan = "\tBuku yang anda cari tidak ditemukan!";
                        }
                    }

                    System.out.print(pesan);
                    break;
                }
                case 2: {
                    String pengarang;
                    String pesan = "";
                    input.nextLine();
                    System.out.print("\tMasukan Nama Pengarang\t: ");
                    pengarang = input.nextLine();
                    System.out.println("\tHASIL PENCARIAN\t: ");

                    for (int i = 0; i < banyakBuku; i++) {
                        if (daftarBuku[i].pengarang.equalsIgnoreCase(pengarang)) {
                            System.out.println("\t Kode Buku\t: " + daftarBuku[i].kode);
                            System.out.println("\tJudul Buku\t: " + daftarBuku[i].judul);
                            System.out.println("\tNama Pengarang\t: " + daftarBuku[i].pengarang);
                            pesan = "";
                            break;
                        } else {
                            pesan = "\tBuku yang anda cari tidak ditemukan!";
                        }
                    }

                    System.out.print(pesan);
                    break;
                }
            }

            System.out.print("\n\tUlangi Pencarian ? [Y/T] : ");
            jawab = input.next().charAt(0);
        } while (jawab == 'y' || jawab == 'Y');
    }
}
